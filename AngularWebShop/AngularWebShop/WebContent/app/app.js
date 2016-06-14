// napravimo modul
var webShop = angular.module('webShop', ['ngRoute']);

webShop.config(function($routeProvider) {
	$routeProvider.when('#/',
	{
		templateUrl: 'index.html'
	}).when('/products',
	{
		//controller: 'shoppingCartController', // inace je podeseno ng-controller atributom
		templateUrl: 'partials/products.html',
		controller : 'productsController'
	})
	.when('/users',{
		templateUrl: 'partials/user.html',
		controller : 'userCtrl'
	})
});

webShop.config(function($logProvider){
    $logProvider.debugEnabled(true);
});