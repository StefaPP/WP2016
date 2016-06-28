webShop.factory('productsFactory', function($http, $window) {

	var factory = {};
	factory.getProducts = function() {
		return $http.get('/AngularWebShop/rest/proizvodi/getJustProducts');
	};
	factory.getProduct = function(id) {
		return $http.get('/AngularWebShop/rest/proizvodi/getProduct/', {
			params : {
				"id" : id
			}
		});
	};
	factory.getCategories = function() {
		console.log("usao u fabriku")
		return $http.get('/AngularWebShop/rest/proizvodi/getCategories');
	};
	factory.addToCart = function(product) {
		return $http.post('/AngularWebShop/rest/proizvodi/add', {
			"id" : '' + product.id,
			"count" : parseInt(product.count)
		});
	};
	factory.addCategory = function(category) {
		return $http.post('/AngularWebShop/rest/proizvodi/addCategory', {
			"name" : '' + category.name,
			"description" : '' + category.description
		});
	};
	factory.addProduct = function(product) {
		return $http.post('/AngularWebShop/rest/proizvodi/addProduct', {
			"name" : product.name,
			"price" : product.price,
			"size" : product.size,
			"weight" : product.weight,
			"origin" : product.origin,
			"brandName" : product.brandName,
			"category" : product.category,
			"image" : product.image,
			"storeId" : product.storeId,
			"lager" : product.lager,
		})
	}
	factory.getProductsForCategory = function(categoryName) {
		return $http.get(
				'/AngularWebShop/rest/proizvodi/getProductsForCategory', {
					params : {
						"name" : categoryName
					}
				})
	};
	
	factory.deleteCategory = function(category)
	{
		return	$http({
		    method : "DELETE",
		    url : '/AngularWebShop/rest/proizvodi/deleteCategory',
		    data : category,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	}
	
	
	factory.getStoreProducts = function(id) {
		return $http.get('/AngularWebShop/rest/proizvodi/getProductsOfStore', {
			params : {
				"id" : id
			}
		})
	};
	
	factory.deleteProduct = function (product){
		return	$http({
		    method : "DELETE",
		    url : '/AngularWebShop/rest/proizvodi/deleteProduct',
		    data : product,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	}
	return factory;

});
webShop.factory('reviewFactory', function($http) {
	var factory = {};

	factory.addReview = function(review) {
		return $http.post('/AngularWebShop/rest/reviews/addReview', {
			"rating" : review.rating,
			"productId" : review.productId,
			"comment" : review.comment
		});
	}
	factory.getReviews = function() {
		return $http.get('/AngularWebShop/rest/reviews/getReviews');
	}
	
	factory.deleteReview = function(review){
		return	$http({
		    method : "DELETE",
		    url : '/AngularWebShop/rest/reviews/deleteReview',
		    data : review,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	}

	return factory;
})

webShop.factory('userFactory', function($http) {
	var factory = {};
	factory.getUsers = function() {
		return $http.get('/AngularWebShop/rest/users/getUsers');
	};
	factory.getUser = function(user) {
		return $http.get('/AngularWebShop/rest/', {
			"username" : '' + user.username
		})
	};
	return factory;
});

webShop.factory('deliveryFactory', function($http) {
	var factory = {};
	factory.getDeliverers = function() {
		return $http.get('/AngularWebShop/rest/delivery/getDeliverers');
	};
	return factory;
})

webShop.factory('storeFactory', function($http) {
	var factory = {};
	factory.getStores = function() {
		return $http.get('/AngularWebShop/rest/stores/getStores');
	};

	factory.addStore = function(store) {
		return $http.post('/AngularWebShop/rest/stores/add', {
			"id" : '' + store.id,
			"name" : '' + store.name,
			"address" : '' + store.address,
			"country" : '' + store.country,
			"contact" : '' + store.contact,
			"email" : '' + store.email,
			"seller" : '' + store.seller
		})
	};
	factory.getStore = function(store) {
		console.log(store);
		return $http.get('/AngularWebShop/rest/stores/getStore', {
			params : {
				"id" : '' + store
			}
		})
	};
	factory.getStoreProducts = function() {
		return $http.get('/AngularWebShop/rest/stores/getStoreProducts', {
			params : {
				"id" : '' + store
			}
		});
	}
	
	factory.deleteStore = function (store){
		//return $http.delete('/AngularWebShop/rest/stores/',{ params :{"id" : id} });
	 return	$http({
		    method : "DELETE",
		    url : '/AngularWebShop/rest/stores/deleteStore',
		    data : store,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	
	}
	
	return factory;
});
webShop.factory('signupFactory',function($http){
	
	var factory = {};
	
	factory.signup = function(user){
		return $http({
		    method : "POST",
		    url : '/AngularWebShop/rest/users/signUp',
		    data : user,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		})
		
}
	return factory
	
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
