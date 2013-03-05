	// ===================== MODELS ==================//

	var TransactionModel = Backbone.Model.extend({
        urlRoot: "transaction"
    });

	var TransactionCollection = Backbone.Collection.extend({
		model : TransactionModel,
		url: 'transactions'
	});

	var PagingModel = Backbone.Model.extend({
		url: 'transactionsPages'
	});
	
	// ===================== END MODELS ==================//
	
	// ======================= VIEWS =====================//
	
	// Form for creating new transaction
	var AddTransactionView = Backbone.View.extend({
        events : {
            "submit #add_transaction" : "submit",
            "click .close" : "closeAlert"
        },
		render : function() {
			renderTemplate({
				selector:this.el, 
				name: "transaction_add",
                data : this.model.toJSON(),
				// turns on form validation after template load
				callback: this.initValidation
			});
		},
        initValidation : function(){
            $("#add_transaction").validationEngine();
            $("#error").hide();
            $("#success").hide();
        },
        submit : function() {

            this.model.set({
                source : this.model.source,
                destination : this.$('#number').val(),
                sum : this.$('#sum').val()
            })
            var self = this;
            this.model.save(null,
                {
                    success: function (model, response) {
                        self.$('#error').hide();
                        self.$('#success').show();
                        self.model.clear();
                        $(self.el).find('input[type=text]').val('');
                    },
                    error: function (model, response) {
                        var errorText = JSON.parse(response.responseText).error;
                        if (errorText === undefined) {
                           errorText = "Unexpected error";
                        }
                        self.$('#success').hide();
                        self.$('#error > .message').text(errorText);
                        self.$('#error').show();
                    }
                }
            );
            return false;
        },

        closeAlert : function(e) {
             $(e.target).parent().hide();
        }

	});
	
	// Client menu for switching between list and transaction creation
	var TransactionMenuView = Backbone.View.extend({
		router: null,
		render : function() {
			renderTemplate({
				selector: this.el, 
				name: "client_menu"
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
			this.pagingModel = new PagingModel();
			this.pagingView = new PagingView({
				el: $("#contentFooter"), 
				model : this.pagingModel
			});
            this.transactionCollection = new TransactionCollection();
            this.transactionsTableView = new TransactionsTableView({
                el: $("#contentBody"),
                collection : this.transactionCollection
            });

            this.transactionModel = new TransactionModel();
            this.addView = new AddTransactionView({el: $("#contentBody"), model: this.transactionModel});
		},
		
		routes : {
			"" : 'index',
			"page/:page" : "page",
			"addTransaction" : "addTransaction"
		},
		
		// index page presented as first page of transactions list
		index : function() {
			this.page(1);
		},

		// shows transactions defined page
		page : function(page) {
			// updates pagination
			this.pagingModel.clear({silent : true});

			this.pagingModel.set({cpage : page});
			this.pagingModel.fetch( { data :{number : this.number}});
			
			// shows transaction table
			this.transactionCollection.fetch({
				data : {
					page : page,
                    number : this.number
				}
			});

		},

		// transaction creation view
		addTransaction : function() {
            this.transactionModel.source = this.number;
			this.addView.render();
			$("#contentFooter").html('');
		}

	});

	// ===================== END ROUTERS ==================//
