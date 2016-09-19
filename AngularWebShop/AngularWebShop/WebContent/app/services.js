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
	
	factory.getDiscountedProduct = function(item) {
		return $http({ 
		method : "POST",
		    url : '/AngularWebShop/rest/proizvodi/getDiscountProduct',
		    data : item,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		})
	}
	
	factory.getDiscountPrice = function(id) {
		return $http.get('/AngularWebShop/rest/proizvodi/discountPrice/', {
			params : {
				"id" : id
			}
		});
	};
	
	factory.getDiscounts = function () {
		return $http.get('/AngularWebShop/rest/proizvodi/discounts');
	}
	
	factory.getCategories = function() {
		console.log("usao u fabriku")
		return $http.get('/AngularWebShop/rest/proizvodi/getCategories');
	};

	factory.buy = function(buying){
		return $http.post('/AngularWebShop/rest/proizvodi/buyProducts',{
			"customerId":''+buying.customerId,
			"storeId":''+buying.storeId,
			"productId":''+buying.productId,
			"deliveryId":''+buying.deliveryId,
			"totalPrice":''+buying.totalPrice
		})
	}
	
	factory.getHistory = function(){
		return $http.get('/AngularWebShop/rest/proizvodi/getBuyingHistory');
	}
	
	factory.getShoppingList = function(){ 
		return $http.get('/AngularWebShop/rest/proizvodi/getShoppingList');
	}
	
	factory.getUsersWishList = function(user){
		console.log(JSON.stringify(user))
		return $http({ 
		method : "POST",
		    url : '/AngularWebShop/rest/proizvodi/getUsersWishList',
		    data : user,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		})
//		return $http.post('/AngularWebShop/rest/proizvodi/getWishList');
	
	}
	
	
	
	factory.addToCart = function(product) {
		return $http.post('/AngularWebShop/rest/proizvodi/addToCart', {
			"customerId": '' + product.customerId,
			"storeId" : '' + product.storeId,
			"productId" : '' + product.productId,
			"deliveryId" : '' + product.deliveryId
		});
	};
	
	factory.addWish = function(product) {
		return $http.post('/AngularWebShop/rest/proizvodi/addWish', {
			"customerId": '' + product.customerId,
			"storeId" : '' + product.storeId,
			"productId" : '' + product.productId
		});
	};
	
	
	
	factory.addCategory = function(category) {
		return $http.post('/AngularWebShop/rest/proizvodi/addCategory', {
			"name" : '' + category.name,
			"description" : '' + category.description
		});
	};
	factory.addProduct = function(product) {
		
		return $http({
		    method : "POST",
		    url : '/AngularWebShop/rest/proizvodi/addProduct',
		    data : product,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});	
	}
	factory.updateProduct = function(product){
		return $http({
			method : "POST",
			url : '/AngularWebShop/rest/proizvodi/updateProduct',
		    data : product,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
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
	
	factory.removeWish = function(customerId,productId) {
		return $http.post('/AngularWebShop/rest/proizvodi/removeWish',
		{
			"productId" :'' + productId,
			"customerId":'' + customerId
		});
		
		
	};
	
	
	return factory;

});
webShop.factory('reviewFactory', function($http) {
	var factory = {};

	factory.addReview = function(review) {
		return $http.post('/AngularWebShop/rest/reviews/addReview', {
			"rating" : review.rating,
			"productId" : review.productId,
			"comment" : review.comment,
			"user" : review.user
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
	
	factory.hireSeller = function(seller){
		return $http({
		    method : "POST",
		    url : '/AngularWebShop/rest/users/hireSeller',
		    data : seller,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	}
	
	return factory;
});

webShop.factory('deliveryFactory', function($http) {
	var factory = {};
	factory.getDeliverers = function() {
		return $http.get('/AngularWebShop/rest/delivery/getDeliverers');
	};
	
	factory.addDeliverer = function(deliverer){
		return $http({
		    method : "POST",
		    url : '/AngularWebShop/rest/delivery/addDeliverer',
		    data : deliverer,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	}
	
	factory.getDeliverer = function(deliveryID){
		 return $http({
		    method : "GET",
		    url : '/AngularWebShop/rest/delivery/addDeliverer',
		    data : deliverer,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	
	}
	
	factory.update = function(deliverer){
		return $http({
		    method : "POST",
		    url : '/AngularWebShop/rest/delivery/updateDeliverer',
		    data : deliverer,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	}
	
	factory.deleteDeliverer = function(deliverer){
		return $http({
		    method : "DELETE",
		    url : '/AngularWebShop/rest/delivery/deleteDeliverer',
		    data : deliverer,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	}
	
	return factory;
})

webShop.factory('storeFactory', function($http) {
	var factory = {};
	factory.getStores = function() {
		return $http.get('/AngularWebShop/rest/stores/getStores');
	};

	factory.addStore = function(store) {
		return $http({
		    method : "POST",
		    url : '/AngularWebShop/rest/stores/add',
		    data : store,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	};
	
	factory.updateStore = function(store){
		return $http({
		    method : "POST",
		    url : '/AngularWebShop/rest/stores/updateStore',
		    data : store,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		});
	}
	
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

webShop.factory('loginFactory',function($http,$localStorage,$log,$location,$route){
	
	var service = {};
	
	service.login = login;
	service.logout = logout;
	service.getCurrentUser = getCurrentUser;
	
	return service;
	
	function login(username,password,callback) {
		console.log(username +  " " + password);
		var user = {};
		user.username = username;
		user.password = password;
		$http({
		    method : "POST",
		    url : '/AngularWebShop/rest/users/login',
		    data : user,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		}).success(function (user){
			if(user.username) {
				var currentUser = {username : username , role : user.role , country : user.country};
				$localStorage.currentUser = currentUser;
				$location.path('/products');
			}else {
				callback(false);
			}
		})
	}
	
	function logout() { 
		delete $localStorage.currentUser;
		$location.path('/login')
	}
	
	function getCurrentUser() {
		return $localStorage.currentUser;
	}
	
});

webShop.factory('saleFactory',function($http){
	var factory = {};
	factory.addOnSale = function(product){
		return $http.post('/AngularWebShop/rest/proizvodi/addOnSale',{
			"productId":''+product.id,
			"storeId":''+product.storeId,
			"startDate":''+product.startDate,
			"endDate":''+product.endDate,
			"discountRate":''+product.discount
		});
	}
	
	return factory;
})

webShop.factory('shoppingListFactory', function($http) {

	var factory = {};
	
	
	factory.sendComplaint = function(complaint){
		return $http({
			method : 'POST',
			url : '/AngularWebShop/rest/proizvodi/sendComplaint',
			data : complaint,
			headers : {
			        'Content-Type' : 'application/json'
			    }
		})
	}
	
	factory.removeItem = function(customerId,productId) {
		return $http.post('/AngularWebShop/rest/proizvodi/removeItem',
		{
			"productId" :'' + productId,
			"customerId":'' + customerId
		});
		
		
	};
	
	factory.getBuyingHistory = function(user) {
		return $http.post('/AngularWebShop/rest/proizvodi/getUsersBuyingHistory',{"customerId" :'' + user});
		
	}
	


	factory.clearShoppingList = function(user){
	return $http.post('/AngularWebShop/rest/proizvodi/clearShoppingList',{
		"customerId":'' + user
	})
	
	}

	
	factory.getUsersShoppingList = function(user){
		return $http.post('/AngularWebShop/rest/proizvodi/getUsersShoppingList', 
				{ "customerId" :''+user })

	};
	
	return factory;

});
