// napravimo modul
var webShop = angular.module('webShop', ['ngRoute']);

webShop.config(function($routeProvider) {
	$routeProvider.when('/',
	{
		//controller: 'productsController',// inace je podeseno ng-controller atributom
		templateUrl: 'partials/products.html'
	}).when('/sc',
	{
		//controller: 'shoppingCartController', // inace je podeseno ng-controller atributom
		templateUrl: 'partials/shoppingCart.html'
	})
});

webShop.config(function($logProvider){
    $logProvider.debugEnabled(true);
});