// Button setting in account info view
var BtnSettings = {
		NEW : {
			title: 'Promote',
			cls: 'btn-primary'
		},
		ACTIVE : {
			title: 'Block',
			cls: 'btn-danger'
		},
		BLOCKED : {
			title: 'Unblock',
			cls: 'btn-success'
		} 
};

var templates = {};

// loads and caches template
var loadTemplate = function(name){
    var template = templates[name] || $.get("../tmpl/" + name + ".tmpl.htm");
    templates[name] = template;
    return template;
}

// gets and renders templates
function renderTemplate(item) {
    loadTemplate(item.name).done(function(contents){
        $.templates({
            template : contents
        });
        $(item.selector).html($.render.template(item.data));
        if (typeof item.callback !== 'undefined') {
            item.callback();
        }
    });
}


$.views.converters({
	sumConverter : function(value) {
		return value.toFixed(2);
	},
	dateConverter : function(value) {
		var d = new Date(value);
		var year = d.getFullYear();
		var month = ('0' + (d.getMonth() + 1)).slice(-2);
		var day = ('0' + d.getDate()).slice(-2);
		var hour = ('0' + d.getHours()).slice(-2);
		var minute = ('0' + d.getMinutes()).slice(-2);
		return year + "-" + month + "-" + day + " " + hour + ":" + minute;
	}
});