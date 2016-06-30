webShop.controller('productsController', function($scope,$location,productsFactory,$rootScope) {
	
    function init() {
    	if($rootScope.isLoggedIn()){
    		$scope.currentUser = $rootScope.getCurrentUser().username;
    		$scope.currentRole = $rootScope.getCurrentUser().role;
    	}
        productsFactory.getProducts().success(function (data) {
        	$scope.products = data;
		});
     
        console.log($scope.currentUser);
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
		});	
	};
	
	$scope.addCategory = function()
	{
		productsFactory.addCategory($scope.category).success(function(){
			initCats();
			$location.path("/products");
		});
		
	}	
	$scope.deleteCategory = function(category) {
		console.log("Deleting this category");
		productsFactory.deleteCategory(category).success(function(){
			initCats();
		})
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
	
	$scope.deleteCategory = function() {
		console.log("Deleting this category");
		productsFactory.deleteCategory(category).success(function(){
			init();
		})
	}
	init();
})
.controller('productDetailsCtrl',function($scope,$location,$rootScope,$routeParams,productsFactory,reviewFactory){
	
	 $scope.starRating = 0;	
	 $scope.click = function (param) {
	        console.log('Click(' + param + ')');
	        $scope.review.rating = param;     
	 };
	 
	 $scope.deleteProduct = function(product) {
		 productsFactory.deleteProduct(product).success(function() {
			 $location.path("/products");
		 })
	 }
	 
	 $scope.deleteReview = function(review) {
		 reviewFactory.deleteReview(review).success(function() {
			 init();
		 })
	 }
	 
	 $scope.addToCart = function(product) {
		 alert('You have successfully added ' + $scope.product.name + ' to cart');
		 product.customerId = $rootScope.getCurrentUser().username;
		 product.productId = $scope.product.id;
		 product.storeId = $scope.product.storeId;
		 
		 console.log(JSON.stringify(product) + "<<<<<<<<<<<")
		 productsFactory.addToCart(product).success(function(data) {
			 init();
		});	

		 $scope.product.lager -= 1; 
	 }
	 
	 $scope.get = function(star){
		 $scope.stars = parseInt(star);
		 return new Array($scope.stars);
		 
	 }
	 	function init() {
	 	if($rootScope.isLoggedIn()){
	 		$scope.loggedIn = true;
	 		$scope.currentUser = $rootScope.getCurrentUser().username;
		 	$scope.currentRole = $rootScope.getCurrentUser().role;
	 	}
	 		
	 		productsFactory.getShoppingList().success(function(data){
	 		$scope.shoppingList = data;
	 		console.log("Shopping list" + JSON.stringify($scope.shoppingList))
	 	});
	 	
		$scope.review = {};
		var id = $routeParams.id;
		$scope.review.productId = id;
		$scope.review.user = $scope.currentUser;
		productsFactory.getProduct(id).success(function(data){	
			$scope.product = data;
		})
		
		reviewFactory.getReviews().success(function(data){
			 $scope.reviews = data;
		 })
	};
	    $scope.getNumber = function(num) {
		num = parseInt($scope.review.rating, 10);
		return new Array(num);   
    }
	
	$scope.addReview = function(review) {
		reviewFactory.addReview($scope.review).success(function() {
		init();
	});
		$scope.review = {};
		$scope.starRating = 0;	
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
		$location.path('/store/'+store.id);	
	}
	$scope.addStore = function() {
		console.log("Dodao sam novu prodavnicu " + $scope.store.name)
		console.log($scope.store);
		storeFactory.addStore($scope.store).success(function(){
			init();
		});
	}
		init();
})
.controller('storeDetailsCtrl',function($scope,$location,$routeParams,$rootScope,storeFactory,productsFactory){
		
		function init() {
		
		if($rootScope.isLoggedIn()){
	 		$scope.currentUser = $rootScope.getCurrentUser().username;
		 	$scope.currentRole = $rootScope.getCurrentUser().role;		 	
		 }
		
		$scope.product = {};
		var id = $routeParams.id;
		console.log("id prodavnice " + id );
		storeFactory.getStore(id).success(function(data){
			$scope.st = data;
		})
		productsFactory.getCategories().success(function (data) {
       		$scope.cats = data;
       		
	 });
		
		productsFactory.getStoreProducts($routeParams.id).success(function(data){
			$scope.storeProducts = data;
			console.log(JSON.stringify($scope.storeProducts) + " <<<<<<<<<<<<<<<");
		})

	}
		$scope.showAddProduct = false;
		$scope.showAddSeller = false;
		$scope.showUpdateStore = false;
		
		
		$scope.addProduct = function(){
		$scope.product.storeId = $routeParams.id;
			productsFactory.addProduct($scope.product).success(function (){
				init();
			});
	}
		$scope.deleteStore = function(store){
			console.log($routeParams.id + " <<<<<<<<<<")
			storeFactory.deleteStore(store).success(function() {
				$location.path('/stores/');
				init();
			})
		}
		
		init();
})
.controller('signupCtrl',function($scope,$location,signupFactory,userFactory){
	
	function init() {
	console.log('Signup Controller')
	userFactory.getUsers().success(function(data){
			$scope.users = data;
		})
	}
	
	init();
	
	$scope.login = function(){
		$location.path('/login');
	}
	$scope.user={};
	$scope.signup=function () {
		signupFactory.signup($scope.user).success(function (){
			$location.path('/login');
		})
	}
})
.controller('loginCtrl',function($scope,$location,loginFactory,userFactory){
	
	function init() {
		console.log('Login Controller')
		userFactory.getUsers().success(function(data){
				$scope.users = data;
			})
		}
		
		init();
	
	$scope.signUp = function(){
		$location.path('/signup');
	}
	$scope.user = {};
	$scope.login = function () {
		console.log($scope.user.username + " " + $scope.user.password)
		loginFactory.login($scope.user.username,$scope.user.password,loginCbck);
	}
	function loginCbck(success){
		if(success){
			console.log('success!');
		}
		else{
			$scope.value = true;
		}
	}
	
})
.controller('shoppingListCtrl',function($scope,$rootScope,shoppingListFactory,productsFactory,deliveryFactory) {
	
	function init(){
		$scope.deliveryId = "";
		$scope.currentUser = $rootScope.getCurrentUser().username;
		$scope.products = []
		deliveryFactory.getDeliverers().success(function(data){
			$scope.deliverers = data;
		})
		shoppingListFactory.getUsersShoppingList($scope.currentUser).success(function(data){
		
			$scope.sp = data;
			 angular.forEach($scope.sp, function(item){
                 productsFactory.getProduct(item.productId).success(function (data){
                	 $scope.products.push(data);
                 })

             })
           
		})
		
		$scope.removeItem = function(productId){
			shoppingListFactory.removeItem($scope.currentUser,productId).success(function(){
				alert('This item has been removed from your shopping list')
				init();
			})
		}
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