	// ===================== MODELS ==================//

	var TransactionModel = Backbone.Model.extend({});

	var TransactionCollection = Backbone.Collection.extend({
		model : TransactionModel,
		url: 'client/ajaxTransactions.html'
	});

	var PagingModel = Backbone.Model.extend({
		url: 'client/ajaxTransactionsPages.html'
	});
	
	// ===================== END MODELS ==================//
	
	// ======================= VIEWS =====================//
	
	// Form for creating new transaction
	var AddTransactionView = Backbone.View.extend({
		render : function() {
			renderTemplate({
				selector:this.el, 
				name: "transaction_add",
				// turns on form validation after template load
				callback: initValidation
			});
		}
	});
	
	// Client menu for switching between list and transaction creation
	var TransactionMenuView = Backbone.View.extend({
		router: null,
		render : function() {
			renderTemplate({
				selector: this.el, 
				name: "client_menu",
			});
		},
		events : {
			"click #transactionListBtn" : "showList",
			"click #transactionAddBtn" : "showAdd"
		},
		
		// shows transaction list
		showList : function() {
			this.router.navigate("", true);
		},
		// shows transaction creation
		showAdd : function() {
			this.router.navigate("addTransaction", true);
		}
			
	});

	// ===================== END VIEWS ==================//

	// ===================== ROUTERS ==================//
	
	var ClientRouter = Backbone.Router.extend({
		initialize: function () { 
			var menuView = new TransactionMenuView({el: $("#transactionMenu")});
			menuView.router = this;
			menuView.render();
			
			this.pagingModel = new PagingModel({});
			this.pagingView = new PagingView({
				el: $("#contentFooter"), 
				model : this.pagingModel
			});
		},
		
		routes : {
			"" : 'index',
			"page/:page" : "page",
			"addTransaction" : "addTransaction"
		},
		
		// index page presented as firts page of transactions list
		index : function() {
			this.page(1);
		},

		// shows transactions defined page
		page : function(page) {
			// updates pagination
			this.pagingModel.clear({silent : true});
			this.pagingModel.set({cpage : page});
			this.pagingModel.fetch();
			
			// shows transaction table
			var transactionCollection = new TransactionCollection();
			var transactionsTableView = new TransactionsTableView({
				el: $("#contentBody"),
				collection : transactionCollection
			});

			transactionCollection.fetch({
				data : {
					page : page
				}
			});

		},

		// transaction creation view
		addTransaction : function() {
			var addView = new AddTransactionView({el: $("#contentBody")});
			addView.render();
			$("#contentFooter").html('');
		}

	});

	// ===================== END ROUTERS ==================//
