webShop.factory('productsFactory', function($http,$window) {
	
	var factory = {};
	factory.getProducts = function() {
		return $http.get('/AngularWebShop/rest/proizvodi/getJustProducts');
	};

	factory.getProduct = function(id){
		return $http.get('/AngularWebShop/rest/proizvodi/getProduct/', { params: { "id" : id }});
	};
	
	factory.getCategories = function(){
		console.log("usao u fabriku")
		return $http.get('/AngularWebShop/rest/proizvodi/getCategories');
	};
	
	factory.addToCart = function(product) {
		return $http.post('/AngularWebShop/rest/proizvodi/add', {"id":''+product.id, "count":parseInt(product.count)});
	};
	
	//Pitanje 
	factory.addCategory = function(category){
		return $http.post('/AngularWebShop/rest/proizvodi/addCategory' ,
		{"name":''+category.name,
		 "description":''+category.description});
	
	};
	return factory;
	
});

webShop.factory('userFactory',function($http) {
	var factory = {};
	factory.getUsers = function() {
		return $http.get('/AngularWebShop/rest/users/getUsers');
	};
	factory.getUser = function(user){
		return $http.get('/AngularWebShop/rest/', {"username":''+user.username})
	};
	return factory;
});

webShop.factory('deliveryFactory',function($http){
	var factory = {};
	factory.getDeliverers = function(){
		return $http.get('/AngularWebShop/rest/delivery/getDeliverers');
	};
	return factory;
})

webShop.factory('storeFactory',function($http){
	var factory = {};
	factory.getStores = function() {
		return $http.get('/AngularWebShop/rest/stores/getStores');
	};
	
	factory.addStore = function(store){
		return $http.post('/AngularWebShop/rest/stores/add',
				{"id":''+store.id,
				"name":''+store.name,
				"address":''+store.address,
				"country":''+store.country,
				"contact":''+store.contact,
				"email":''+store.email,
				"seller":''+store.seller})
		};
	factory.getStore = function(store){
		console.log(store);
		return $http.get('/AngularWebShop/rest/stores/getStore',{params : {"id":''+store}})
		
	};
	
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
