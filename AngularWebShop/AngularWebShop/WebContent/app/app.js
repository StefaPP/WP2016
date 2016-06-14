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
	.when('/users',{
		templateUrl: 'partials/user.html',
		controller : 'userCtrl'
	})
	.when('/stores',{
		templateUrl : 'partials/stores.html',
		controller : 'storeCtrl'
	})
});

webShop.config(function($logProvider){
    $logProvider.debugEnabled(true);
});