	// ===================== MODELS ==================//

	var AccountModel = Backbone.Model.extend({});
	
	// Data for accounts table rows
	var AccountsCollection = Backbone.Collection.extend({
		model : AccountModel,
		url : 'admin/ajaxAccounts.html'
	});
	
	var PagingModel = Backbone.Model.extend({
		url: 'admin/ajaxAccountsPages.html'
	});
	
	var TransactionCollection = Backbone.Collection.extend({
	});
	
	// Detailed account info
	var AccountInfoModel = Backbone.Model.extend({
		url : 'admin/ajaxAccount.html', 
		transactions : new TransactionCollection(),
	
		// sets 'transactions' attribute as collection
		set: function(attributes, options) {
		    if (attributes.transactions !== undefined) {
		    	this.transactions.reset(attributes.transactions);
		    	delete attributes.transactions;
		    }
		    return Backbone.Model.prototype.set.call(this, attributes, options);
		},
	
		// calls account switch status on server
		changeStatus: function() {
		    var self = this;
		    $.ajax({
		        type: 'POST',
		        url: 'admin/ajaxAccountUpdateStatus.html',
		        data: { 
		        	// account number
		            'number': self.get('number'),
		            // current status
		            'status': self.get('status')
		        },
		        success: function(data) {
		        	self.set({
		        		'status': data.status, 
		        		'settings': BtnSettings[data.status]
		        		});
		        },
		    });
		}
	});
	
	// ===================== END MODELS ==================//

	// ======================= VIEWS =====================//
	
	var AccountsTableView = Backbone.View.extend({
		// re-renders view after collection data load
		initialize : function() {
			this.collection.on('reset', this.render, this);
		},
		events : {
			"click .more-info" : "info"
		},
	
		// shows account detailed info
		info : function(e) {
			var number = e.currentTarget.attributes['number'].value;
			this.router.navigate("account/" + number, true);
		},
		
		render : function(options) {
			var item = {
					selector: this.el,
					name: 'accounts_table', 
					data : {accounts: this.collection.toJSON()}
				};
			renderTemplate(item);
		}
	});
	
	// View for detailed account information
	var AccountView = Backbone.View.extend({
		initialize : function() {
			this.model.bind('change', this.render, this);
		},
		events : {
			"click #action" : "action",
			"click #accounts-list" : "list"
		},
		// returns to accounts list
		list: function() {
			this.router.navigate("", true);
		},
		// calls account status change
		action : function() {
			this.model.changeStatus();
		},
		
		render : function() {
			renderTemplate({
				selector:this.el, 
				name: "account",
				data : this.model.toJSON()
			});
		}
	});
	
	// ===================== END VIEWS ==================//

	// ===================== ROUTERS ==================//
	var AdminRouter = Backbone.Router.extend({
		initialize: function () { 
			this.pagingModel = new PagingModel({});
			this.pagingView = new PagingView({el: $("#contentFooter"), model : this.pagingModel});
		},
		
		routes : {
			"" : 'index',
			"page/:page" : "page",
			"account/:account" : "account"
		},

		//index page presented as first page of accounts list
		index : function() {
			this.page(1);
		},

		// shows accounts defined page
		page : function(page) {
			
			this.pagingModel.clear({silent : true});
			this.pagingModel.set({cpage : page});
			this.pagingModel.fetch();
			
			var accountsCollection = new AccountsCollection();
			var accountsTableView = new AccountsTableView({
				el: $("#contentBody"),
				collection : accountsCollection
			});
			accountsTableView.router = this;
			accountsCollection.fetch({
				data : {
					page : page
				}
			});

		},

		// shows account detailed information
		account : function(account) {
			
			var accountInfoModel = new AccountInfoModel();
			
			var accountView = new AccountView({
				el: $("#contentBody"), 
				model : accountInfoModel
			});
			accountView.router = this;
			
			var transactionsTableView = new TransactionsTableView({
				el: $("#contentFooter"), 
				collection : accountInfoModel.transactions
			});
			
			accountInfoModel.fetch({
				data : {
					number : account
				},
				success: function(model, responce) {
					accountInfoModel.set({
		        		'settings': BtnSettings[responce.status]
		        		});
		        }
			});
		}

	});

	// ===================== END ROUTERS ==================//
