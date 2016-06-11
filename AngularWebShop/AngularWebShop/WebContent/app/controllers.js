

// controller
webShop.controller('productsController', function($scope, productsFactory) {
	
    function init() {
    	console.log('ProductsController.Init');
        productsFactory.getProducts().success(function (data) {
        	$scope.products = data;
		});
        //$scope.products = [{"id":"3","name":"Samsung monitor 17\"","price":35000.0},{"id":"2","name":"Sony digitalna kamera","price":32000.0},{"id":"1","name":"Televizor marke Sony, 51 cm dijagonala","price":22000.0},{"id":"4","name":"Pioneer DVD pisac","price":7100.0}]
    }

	init();
	
	$scope.addToCart = function(product) {
		productsFactory.addToCart(product).success(function(data) {
			toast('Product ' + product.name + " added to the Shopping Cart");
		});	
	}; 
});

webShop.controller('shoppingCartController', function($scope, shoppingCartFactory) {
	
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
});