// napravimo modul
var webShop = angular.module('webShop', ['ngRoute','ngStorage','ngFileUpload','pageslide-directive','ngMaterial','rzModule']);

webShop
.config(config)
.run(run);

function config($routeProvider) {
	$routeProvider
	.when('/',{
		templateUrl: 'partials/login.html',
		controller : 'loginCtrl'
	})
	.when('#/',{
		templateUrl: 'index.html',
		controller : 'productsController'
	})
	.when('/signup',{
		templateUrl: 'partials/signup.html',
		controller : 'signupCtrl'
	})
	.when('/login',{
		templateUrl: 'partials/login.html',
		controller : 'loginCtrl'
	})
	.when('/products',{
		templateUrl: 'partials/products.html',
		controller : 'productsController'
	})
	.when('/product/:id',{
		templateUrl: 'partials/productDetails.html',
		controller : 'productDetailsCtrl'
	})
	.when('/updateProduct/:id',{
		templateUrl: 'partials/updateProduct.html',
		controller : 'productDetailsCtrl'
	})
	.when('/complaints',{
		templateUrl: 'partials/complaints.html',
		controller : 'complaintsCtrl'
	})
	.when('/stores',{
		templateUrl : 'partials/stores.html',
		controller : 'storeCtrl'
	})
	.when('/store/:id',{
		templateUrl : 'partials/store.html',
		controller : 'storeDetailsCtrl'
	})
	.when('/category/:name',{
		templateUrl : 'partials/categoryProducts.html',
		controller : 'categoryProductsCtrl'
	})
	.when('/delivery/',{
		templateUrl : 'partials/delivery.html',
		controller : 'deliveryCtrl'
	})
	.when('/category/',{
		templateUrl : 'partials/category.html',
		controller : 'productsController'
	})
	.when('/shoppingList',{
		templateUrl : 'partials/shoppingList.html',
		controller : 'shoppingListCtrl'
	})
	.when('/sale',{
		templateUrl : 'partials/sale.html',
		controller : 'saleCtrl'
	})
	.when('/updateCategory/:catName',{
		templateUrl : 'partials/updateCategory.html',
		controller : 'productsController'
	})
}

function run($rootScope,$http,$location,$localStorage,$route,loginFactory){
	
	
	$rootScope.checked=false;
	$rootScope.toggle = function(){
		console.log("alo");
		$rootScope.checked = !$rootScope.checked;
	}
	 
	 $rootScope.logout = function () {
		 console.log("Logged Out")
         loginFactory.logout();
     }
	
	$rootScope.getCurrentUser = function(){
		if(!loginFactory.getCurrentUser()){
			return undefined;
		}
		else {
			return loginFactory.getCurrentUser();
		}
	}
	
	$rootScope.getCurrentUserRole = function () {
        if (!loginFactory.getCurrentUser()){
          return undefined;
        }
        else{
          return loginFactory.getCurrentUser().role;
        }
    }
		
	 $rootScope.isLoggedIn = function () {
         if (loginFactory.getCurrentUser()){
           return true;
         }
         else{
           return false;
         }
     }
}