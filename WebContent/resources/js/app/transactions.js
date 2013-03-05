// Client transactions table
var TransactionsTableView = Backbone.View.extend({
	initialize : function() {
		// Re-renders table after collection data load
		this.collection.on('reset', this.render, this);
	},
	render : function() {
		var item = {
			selector : this.el,
			name : 'transaction_table',
			data : {
				data : this.collection.toJSON()
			}
		};
		renderTemplate(item);
	}
});