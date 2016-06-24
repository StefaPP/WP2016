webShop.controller('productsController', function($scope, productsFactory) {
	
    function init() {
        productsFactory.getProducts().success(function (data) {
        	$scope.products = data;
		});
    }
	init();
	
	function initCats() {
		productsFactory.getCategories().success(function (data) {
       		$scope.categories = data;
       		$scope.category = {};
	 });
	}
	
	initCats();
	
	$scope.addToCart = function(product) {
		productsFactory.addToCart(product).success(function(data) {
			toast('Product ' + product.name + " added to the Shopping Cart");
		});	
	};
	
	$scope.addCategory = function()
	{
		productsFactory.addCategory($scope.category);
		initCats();
	}
	
	
	
})
.controller('categoryProductsCtrl',function($scope,$routeParams,productsFactory){
		
	function init() { 
		productsFactory.getCategories().success(function (data) {
       		$scope.categories = data;
       		$scope.category = {};
	 });
		
		productsFactory.getProductsForCategory($routeParams.name).success(function(data){
			$scope.categoryProducts = data;
		})
	}
	init();
})
.controller('productDetailsCtrl',function($scope,$routeParams,productsFactory,reviewFactory){
	
	 $scope.starRating = 0;	
	 $scope.click = function (param) {
	        console.log('Click(' + param + ')');
	        $scope.review.rating = param;     
	 };
	 
	 
	 $scope.get = function(star){
		 $scope.stars = parseInt(star);
		// console.log("stars : " + stars)
		 return new Array($scope.stars);
	 }
	 
	 	function init() { 
		console.log('ProductDetailsController.Init');
		$scope.review = {};
		var id = $routeParams.id;
		$scope.review.productId = id;
		console.log(id);
		
		productsFactory.getProduct(id).success(function(data){	
			$scope.product = data;
		})
		
		reviewFactory.getReviews().success(function(data){
			 $scope.reviews = data;
		 })

	};
	
		//$scope.rtng = parseInt($scope.review.rating, 10);
	    $scope.getNumber = function(num) {
		num = parseInt($scope.review.rating, 10);
		return new Array(num);   
    }
	
	$scope.addReview = function(review) {
		console.log("Ovo je review " + JSON.stringify($scope.review));
		reviewFactory.addReview($scope.review);
		$scope.review = {};
		$scope.starRating = 0;	
		init();
	}
	
	
	init();
})
.controller('shoppingCartController', function($scope, shoppingCartFactory) {
	
    function init() {
    	console.log('ShoppingCartController.Init');
        shoppingCartFactory.getSC().success(function (data) {
        	$scope.sc = data;
		});

        shoppingCartFactory.getTotal().success(function(data) {
        	$scope.total = data;
        });
    }

	init();
	
	$scope.clearSc = function() {
		if (confirm('Da li ste sigurni?') == true) {
	    	shoppingCartFactory.clearSc().success(function(data) {
	    		$scope.sc = {};
	    		$scope.total = 0.0;
	    	});
		}
    };
})
.controller('userCtrl',function($scope,userFactory){
	function init() { 
		console.log('User Controller');
		userFactory.getUsers().success(function(data){
			$scope.users = data;
		})
	}
	init();
})
.controller('storeCtrl',function($scope,$window,$location,$routeParams,storeFactory){
	
	function init() {
		console.log('Store Controller');
		storeFactory.getStores().success(function(data){
		$scope.store = {};
		$scope.stores = data;
			
	});
}
	
	$scope.storeDetails = function (store) {
		console.log("Usao u je u storeDetails "+ store.id);
		$location.path('/store/'+store.id);
		
	}
	
	$scope.addStore = function() {
		console.log("Dodao sam novu prodavnicu " + $scope.store.name)
		console.log($scope.store);
		storeFactory.addStore($scope.store);
		init();
		$window.location.href('/stores');
	}

		init();
})
.controller('storeDetailsCtrl',function($scope,$location,$routeParams,storeFactory){
		function init() {
		var id = $routeParams.id;
		console.log("id prodavnice " + id );
		storeFactory.getStore(id).success(function(data){
			$scope.st = data;
		})
	}
		init();
})
.controller('deliveryCtrl',function($scope,deliveryFactory){
	function init() {
	console.log('DeliveryCtrl');
	deliveryFactory.getDeliverers().success(function(data){
		$scope.deliverers = data;
	})	
}
	init();
})