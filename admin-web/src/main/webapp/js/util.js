function getParam(form) {
	var o="";
	$.each(form.serializeArray(), function(index) {
					o =o+"&"+this['name']+'='+ this['value'];
			});
	return o;
};