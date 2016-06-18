// napravimo modul
var webShop = angular.module('webShop', ['ngRoute']);

webShop.config(function($routeProvider) {
	$routeProvider.when('#/',{
		templateUrl: 'index.html'
	})
	.when('/products',{
		templateUrl: 'partials/products.html',
		controller : 'productsController'
	})
	.when('/product/:id',{
		templateUrl: 'partials/productDetails.html',
		controller : 'productDetailsCtrl'
	})
	.when('/users',{
		templateUrl: 'partials/user.html',
		controller : 'userCtrl'
	})
	.when('/stores',{
		templateUrl : 'partials/stores.html',
		controller : 'storeCtrl'
	})
	.when('/store/:id',{
		templateUrl : 'partials/store.html',
		controller : 'storeDetailsCtrl'
	})
	.when('/delivery/',{
		templateUrl : 'partials/delivery.html',
		controller : 'deliveryCtrl'
	})
});

webShop.config(function($logProvider){
    $logProvider.debugEnabled(true);
});