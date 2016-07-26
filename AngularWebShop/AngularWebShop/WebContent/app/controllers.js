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
		productsFactory.getShoppingList().success(function(data){
	 	$scope.shoppingList = data;
		productsFactory.addToCart(product).success(function(data) {
		});	
	})
}
	
	$scope.checked=false;
	$scope.toggle = function(){
		console.log("alo");
		$scope.checked = !$scope.checked;
	}
	
	
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
.controller('categoryProductsCtrl',function($scope,$rootScope,$routeParams,productsFactory){
		
	function init() { 
		if($rootScope.isLoggedIn()){
    		$scope.currentUser = $rootScope.getCurrentUser().username;
    		$scope.currentRole = $rootScope.getCurrentUser().role;
    	}
		
		productsFactory.getCategories().success(function (data) {
       		$scope.categories = data;
       		$scope.category = {};
	 });
		
		productsFactory.getProductsForCategory($routeParams.name).success(function(data){
			$scope.categoryProducts = data;
		})
	}
	
	$scope.deleteCategory = function(category) {
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
/*.controller('shoppingCartController', function($scope, shoppingCartFactory) {
	
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
})*/
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
		$scope.store = {};
		storeFactory.getStores().success(function(data){
		$scope.stores = data;			
	});
}
	$scope.storeDetails = function (store) {
		$location.path('/store/'+store.id);	
	}
	
	$scope.addStore = function() {
		console.log("Dodao sam novu prodavnicu " + $scope.store.name)
		storeFactory.addStore($scope.store).success(function(){
			init();
		});
	}
	
		init();
})
.controller('storeDetailsCtrl',function($scope,$location,$routeParams,$rootScope,$timeout,Upload,userFactory,storeFactory,productsFactory){
		
		function init() {
		
		userFactory.getUsers().success(function(data){
				$scope.users = data;
			})
		if($rootScope.isLoggedIn()){
	 		$scope.currentUser = $rootScope.getCurrentUser().username;
		 	$scope.currentRole = $rootScope.getCurrentUser().role;		 	
		 }
		
		$scope.newSeller = {};
		$scope.product = {};
		
		var id = $routeParams.id;
		storeFactory.getStore(id).success(function(data){
			$scope.st = data;
		})
		productsFactory.getCategories().success(function (data) {
       		$scope.cats = data;
       		
	 });
		
		productsFactory.getStoreProducts($routeParams.id).success(function(data){
			$scope.storeProducts = data;
			
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
		
		$scope.updateStore = function(){
			console.log("Update store method for:" + JSON.stringify($scope.store))
			storeFactory.updateStore($scope.st).success(function (){
				init();
			})
		}
		
		$scope.hireSeller = function(seller){
			
			$scope.st.seller = $scope.newSeller.username;
			console.log(JSON.stringify($scope.st));
			userFactory.hireSeller($scope.newSeller).success(function() {
				init();
				$scope.updateStore();
			})	
		}
		
		$scope.uploadFiles = function(file, errFiles) {
	        $scope.f = file;
	        $scope.errFile = errFiles && errFiles[0];
	        if (file) {
	            file.upload = Upload.upload({
	                url: '/AngularWebShop/rest/proizvodi/upload',
	                data: file,
	                headers: {
	                    'Content-Type': 'application/json'
	           }
	            });
	            
	            console.log(JSON.stringify(file) + " <<<<<<")
	            
	            file.upload.then(function (response) {
	                $timeout(function () {
	                    file.result = response.data;
	                });
	            }, function (response) {
	                if (response.status > 0)
	                    $scope.errorMsg = response.status + ': ' + response.data;
	            }, function (evt) {
	                file.progress = Math.min(100, parseInt(100.0 * 
	                                         evt.loaded / evt.total));
	            });
	        }   
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
.controller('loginCtrl',function($scope,$location,loginFactory,userFactory,productsFactory){
	
	function init() {
		//console.log('Login Controller')
		userFactory.getUsers().success(function(data){
				$scope.users = data;
			})
		productsFactory.getWishList().success(function(data){
	 	$scope.wishList = data;
	
		})
		}
		
		init();
	
	$scope.signUp = function(){
		$location.path('/signup');
	}
	$scope.user = {};
	$scope.login = function () {
	//	console.log($scope.user.username + " " + $scope.user.password)
		loginFactory.login($scope.user.username,$scope.user.password,loginCbck);
	}
	function loginCbck(success){
		if(success){
		//	console.log('success!');
		}
		else{
			$scope.value = true;
		}
	}
	
})
.controller('shoppingListCtrl',function($scope,$rootScope,shoppingListFactory,productsFactory,deliveryFactory) {
	
	function init(){
		$scope.deliveryId = "";
		$scope.buying = {};
		
		$scope.currentUser = $rootScope.getCurrentUser().username;
		$scope.products = [];
		$scope.boughtProducts = [];
		$scope.user = {};
		$scope.user = $rootScope.getCurrentUser();
		console.log($scope.user);
		
		deliveryFactory.getDeliverers().success(function(data){
			$scope.deliverers = data;
		})
		
		productsFactory.getHistory().success(function(data){
			$scope.buyingHistory = data;
		})
		
		
		
		shoppingListFactory.getBuyingHistory($scope.currentUser).success(function(data){
			$scope.bh = data;
			angular.forEach($scope.bh,function(item){
				productsFactory.getProduct(item.productId).success(function(data){
					$scope.boughtProducts.push(data);
				})
			})
		})
		
		shoppingListFactory.getUsersShoppingList($scope.currentUser).success(function(data){
			$scope.total = 0;
			$scope.sp = data;
			 angular.forEach($scope.sp, function(item){
                 productsFactory.getProduct(item.productId).success(function (data){
                	 $scope.products.push(data);
                	 $scope.total+=data.price;
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
		
		$scope.buy = function() {
		$scope.buying.customerId = $scope.currentUser;
		angular.forEach($scope.products,function(item) { 
			$scope.buying.storeId = item.storeId;
			$scope.buying.productId = item.id;
			$scope.buying.deliveryId = item.deliveryId;
			$scope.buying.totalPrice = $scope.total;
			console.log(JSON.stringify($scope.buying));
			productsFactory.buy($scope.buying)
		})
			alert('Thank you for shopping with us <3');
			shoppingListFactory.clearShoppingList($scope.buying.customerId).success(function(){
				init();
			});
	}
	
	init();
})
.controller('deliveryCtrl',function($scope,deliveryFactory){
	function init() {
	console.log('DeliveryCtrl');
	$scope.deliverer = {};
	deliveryFactory.getDeliverers().success(function(data){
		$scope.deliverers = data;
	})	
}	
	$scope.value = false;
	$scope.addDeliverer = function(){
		$scope.updateValue = false;
		console.log("Add deliverer ! ");
		deliveryFactory.addDeliverer($scope.deliverer).success(function(){
			init();
		})
	}
	
	$scope.deleteDeliverer = function(deliverer){
		console.log("Delete deliverer ! ");
		deliveryFactory.deleteDeliverer(deliverer).success(function(){
			init();
		})
	}
	$scope.updateValue = false;
	$scope.updateDeliverer = function(deliverer){
		$scope.value = false;
		$scope.updateValue = $scope.updateValue ? false : true;
		console.log("Update deliverer");
	
		console.log(JSON.stringify(deliverer))
		$scope.updDelivery = deliverer;
		
		
	}
	
	$scope.update = function(){
		deliveryFactory.update($scope.updDelivery).success(function(){
			init();
			$scope.updateValue = false;
		})
	}
	
	init();
})