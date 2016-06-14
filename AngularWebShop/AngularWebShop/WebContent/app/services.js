webShop.factory('productsFactory', function($http) {
	
	var factory = {};
	factory.getProducts = function() {
		return $http.get('/AngularWebShop/rest/proizvodi/getJustProducts');
	};

	factory.addToCart = function(product) {
		return $http.post('/AngularWebShop/rest/proizvodi/add', {"id":''+product.id, "count":parseInt(product.count)});
	};
	
	return factory;
	
});

webShop.factory('userFactory',function($http) {
	var factory = {};
	factory.getUsers = function() {
		return $http.get('/AngularWebShop/rest/users/getUsers');
	}
	factory.getUser = function(){
		return $http.get('/AngularWebShop/rest/', {"username":''+user.username})
	}
	return factory;
});

webShop.factory('shoppingCartFactory', function($http) {

	var factory = {};
	factory.getSC = function() {
		return $http.get('/AngularWebShop/rest/proizvodi/getJustSc');
	};
	
	factory.getTotal = function() {
		return $http.get('/AngularWebShop/rest/proizvodi/getTotal');
	};
	
	factory.clearSc = function() {
		return $http.post('/AngularWebShop/rest/proizvodi/clearSc');
	};
	return factory;
	
});
