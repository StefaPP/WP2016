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
.controller('productDetailsCtrl',function($scope,$routeParams,productsFactory){
		
	function init() { 
		console.log('ProductDetailsController.Init');
		var id = $routeParams.id;
		console.log(id);
		productsFactory.getProduct(id).success(function(data){	
			$scope.product = data
		})
	};
	
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
.controller('storeCtrl',function($scope,$location,$routeParams,storeFactory){
	
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