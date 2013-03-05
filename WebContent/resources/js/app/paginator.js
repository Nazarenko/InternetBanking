var PagingView = Backbone.View.extend({
	initialize : function() {
		// Re-renders pagination view if pages count was changed
		this.model.bind('change:pages', this.render, this);
	},
	el : null,
	render : function() {
		var item = {
			selector : this.el,
			name : 'paginator',
			data : {
				// pages count
				pages : _.range(this.model.get("pages")),
				// current page
				cpage : this.model.get('cpage')
			}
		};
		renderTemplate(item);
	}
});
