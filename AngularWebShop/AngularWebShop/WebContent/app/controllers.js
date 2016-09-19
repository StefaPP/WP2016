webShop.controller('productsController', function($rootScope,$scope,$location,$rootScope,productsFactory,shoppingListFactory) {
	
    function init() {
 
	    $scope.slider = {
		  min: 0,
		  max: 100000,
		  options: {
			step: 1000,
		    floor: 0,
		    ceil: 100000
		  }
		};
	    
	    productsFactory.getProducts().success(function (data) {
        	$rootScope.products = data;
		});
	    
	    
	    $rootScope.products = [];
    
		if($rootScope.isLoggedIn()){
    		$scope.currentUser = $rootScope.getCurrentUser().username;
    		$scope.currentRole = $rootScope.getCurrentUser().role;
    		$scope.usersWishList = $rootScope.getCurrentUser();
    		$rootScope.wishListProducts = [];
    		
    		productsFactory.getUsersWishList($scope.usersWishList).success(function(data){
    		 	$scope.wishList = data;		
    		 	console.log($scope.wishList);
    		 	angular.forEach($scope.wishList,function(item){
				productsFactory.getProduct(item.productId).success(function(data){
					$rootScope.wishListProducts.push(data);
					
				})
			})
    	})
    	}
    
    }
	init();
	
	function initCats() {
		productsFactory.getCategories().success(function (data) {
       		$scope.categories = data;
       		$scope.category = {};
    
	 });
	}
	
	initCats();
	

	$rootScope.removeWish = function(productId){
		productsFactory.removeWish($scope.currentUser,productId).success(function(){
				init();
		})
	}	
	
	$rootScope.addToCart = function(product) {
		 product.customerId = $rootScope.getCurrentUser().username;
		 product.productId = product.id;
		 productsFactory.addToCart(product).success(function(data) {
			 shoppingListFactory.getUsersShoppingList(product.customerId).success(function(data){
					$scope.total = 0;
					$scope.sp = data;
					 angular.forEach($scope.sp, function(item){
		                 productsFactory.getProduct(item.productId).success(function (data){
		                	 $rootScope.products.push(data);
		                	 $scope.total += $rootScope.products.slice(-1)[0].price;
		                 })
		                 
		             })
		           
				})
			 productsFactory.removeWish($scope.currentUser,product.productId).success(function(){
				 init();
			})
		});	
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
		
		$scope.slider = {
				  min: 0,
				  max: 100000,
				  options: {
					step: 1000,
				    floor: 0,
				    ceil: 100000
				  }
				};
	
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
.controller('saleCtrl',function($scope,$rootScope,saleFactory,productsFactory){
	
	initCats();
	init();
	
	$scope.slider = {
			  min: 0,
			  max: 100000,
			  options: {
				step: 1000,
			    floor: 0,
			    ceil: 100000
			  }
			};
	
	function initCats() {
		productsFactory.getCategories().success(function (data) {
       		$scope.categories = data;
       		$scope.category = {};
    
	 });
	}
	
	function init() {
		$scope.discounts = {};
		$scope.products = {};
		$rootScope.productsOnSale = [];
		$rootScope.productsToBeOnSale = [];
		$rootScope.productsWereOnSale = [];
		
		$rootScope.dis = [];
		$rootScope.ndis = [];
		$rootScope.wdis = [];
		
		productsFactory.getDiscounts().success(function(data){
			$scope.discounts = data;
			
		var currentDate = new Date();	
		currentDate.setHours(0);
		
		angular.forEach($scope.discounts,function(item){
			item.startDate = new Date(item.startDate.replace(/-/g,"/"));
			item.endDate = new Date(item.endDate.replace(/-/g,"/"));
			
			console.log("Is this" + item.startDate +" after " + currentDate + " and before " + item.endDate);
			
			if(item.startDate <= currentDate && item.endDate >= currentDate ){
				console.log(item.startDate)
				$rootScope.dis.push(item);
			}
			else if(item.startDate >= currentDate){
				$rootScope.ndis.push(item);
			}
			
			else {
				$rootScope.wdis.push(item);
				console.log(item.productId)
				console.log("Skinut je sa rasprodaje !")
			}
			
			
		})
		angular.forEach($rootScope.dis,function(item){
			productsFactory.getDiscountedProduct(item).success(function(data){
				data.discountRate = item.discountRate;
				data.newPrice = data.price - (data.price * (item.discountRate/100));
				console.log("This is the product " + JSON.stringify(data));
				$rootScope.productsOnSale.push(data);
					})
				})
		angular.forEach($rootScope.wdis,function(item){
				productsFactory.getProduct(item.productId).success(function(data){
					data.discountRate = item.discountRate;
					console.log("This is the product " + JSON.stringify(data));
					$rootScope.productsWereOnSale.push(data);
							})
						})	
			})
		}
	
})
.controller('productDetailsCtrl',function($scope,$location,$rootScope,$routeParams,saleFactory,productsFactory,reviewFactory,storeFactory){
	
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
	 
	 $scope.update = function (id){
		 $location.url("/updateProduct/" + id)
	 }
	 
	 $scope.updateProduct = function(product){
		 productsFactory.updateProduct(product).success(function(){
			 console.log("Product has been successfuly updated")
		 })
	 }
	 
	 
	 $scope.deleteReview = function(review) {
		 reviewFactory.deleteReview(review).success(function() {
			 init();
		 })
	 }
	 
	 $rootScope.removeWish = function(productId){
		 $scope.usersWishList = $rootScope.getCurrentUser();
		 productsFactory.removeWish($scope.currentUser,productId).success(function(){
		 productsFactory.getUsersWishList($scope.usersWishList).success(function(data){
 		 	$scope.wishList = data;		
 		 	$rootScope.wishListProducts = [];
 		 	angular.forEach($scope.wishList,function(item){
				productsFactory.getProduct(item.productId).success(function(data){
					$rootScope.wishListProducts.push(data);
					
				})
			})
 	})
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
	 }
	 
	 $scope.addWish = function(product){
		 product.customerId = $rootScope.getCurrentUser().username;
		 $scope.usersWishList = $rootScope.getCurrentUser();
		 product.productId = $scope.product.id;
		 product.storeId = $scope.product.storeId;
		 productsFactory.addWish(product).success(function(data) {
			alert("This item was added to wishlist")
			
			productsFactory.getUsersWishList($scope.usersWishList).success(function(data){
	    		 	$scope.wishList = data;		
	    		 	$rootScope.wishListProducts = [];
	    		 	angular.forEach($scope.wishList,function(item){
					productsFactory.getProduct(item.productId).success(function(data){
					$rootScope.wishListProducts.push(data);
						
					})
				})
	    	})
			 
			 init();
		});	
		 

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
	 		productsFactory.getCategories().success(function (data) {
	       		$scope.cats = data;
	       		
		 });
	 		
		$scope.review = {};
		var id = $routeParams.id;
		$scope.review.productId = id;
		$scope.review.user = $scope.currentUser;
		$scope.newPrice = "";
		$scope.product = {};
		$scope.store =  {};
		$rootScope.discountProduct = {};
		
	
		
		productsFactory.getDiscountPrice(id).success(function(data){
			$rootScope.discountProduct = data;
			if($rootScope.discountProduct) {
			var currentTime = new Date();
			$rootScope.discountProduct.startDate = new Date($rootScope.discountProduct.startDate.replace(/-/g,"/"));
			$rootScope.discountProduct.endDate = new Date($rootScope.discountProduct.endDate.replace(/-/g,"/"));
			if($rootScope.discountProduct.startDate <= currentTime && $rootScope.discountProduct.endDate >= currentTime)
				$rootScope.discountProduct.show = true;
				$scope.newPrice = $scope.product.price - ($scope.product.price * ($rootScope.discountProduct.discountRate/100));
			}
		})
		
		productsFactory.getProduct(id).success(function(data){	
			$scope.product = data;
			var currentTime = new Date();
			console.log("Is " + $rootScope.discountProduct.startDate + " equal to " + currentTime)
			
			if($scope.discountProduct && $rootScope.discountProduct.startDate <= currentTime && $rootScope.discountProduct.endDate >= currentTime){
				console.log("Is " + $rootScope.discountProduct.startDate + " equal to " + currentTime)
			$scope.newPrice = $scope.product.price - ($scope.product.price * ($rootScope.discountProduct.discountRate/100));
		}
		
			storeFactory.getStore($scope.product.storeId).success(function(data){
				$scope.store = data;
				console.log("> " + JSON.stringify($scope.store))
			})
		
	})
		
		reviewFactory.getReviews().success(function(data){
			 var productScore = 0;
			 $scope.reviews = data;
			 var cnt = 0;
			 angular.forEach($scope.reviews,(function(item){
				 if(item.productId != $scope.review.productId){
					 return;
				 }
				 else {
					cnt++;
		    		productScore += parseInt(item.rating);
				    console.log("Trenutna ocena :" + productScore + "\n a counter " + cnt)
				 }
			 }))
			 $scope.productScore = productScore/cnt;
			 $scope.productScore = Math.round( $scope.productScore* 10) / 10
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
	
	$scope.startDate = "";
	$scope.endDate   = "";
	$scope.discount =  "";
	$scope.myDate = new Date();
	  $scope.minDate = new Date(
	      $scope.myDate.getFullYear(),
	      $scope.myDate.getMonth(),
	      $scope.myDate.getDate());
	  $scope.maxDate = new Date(
	      $scope.myDate.getFullYear(),
	      $scope.myDate.getMonth() + 2,
	      $scope.myDate.getDate());
	  $scope.maxDate2 = new Date(
		  $scope.maxDate.getFullYear(),
		  $scope.maxDate.getMonth()+2,
		  $scope.maxDate.getDate());
	
	  $scope.addOnSale = function(product){
		console.log("Adding on sale " + JSON.stringify(product) + " from " + $scope.startDate + " to " + $scope.endDate + " with a discount of " + $scope.discount);
		product.startDate = $scope.startDate;
		product.endDate = $scope.endDate;
		product.discount = $scope.discount
		saleFactory.addOnSale(product).success(function(){
			init();
		})
	  
	  }
	
	init();
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
.controller('loginCtrl',function($scope,$location,loginFactory,userFactory){
	
	function init() {
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
	loginFactory.login($scope.user.username,$scope.user.password,loginCbck);
	}
	function loginCbck(success){
		if(success){
		}
		else{
			$scope.value = true;
		}
	}
	
})
.controller('shoppingListCtrl',function($scope,$rootScope,$location,shoppingListFactory,productsFactory,deliveryFactory) {
	
	function init(){
		$scope.deliveryId = "";
		$scope.buying = {};
		$scope.complaint = {};
		$scope.currentUser = $rootScope.getCurrentUser().username;
		
		$rootScope.products = [];
		$rootScope.discountProducts = [];
		$rootScope.newPrices = [];
		
		$scope.boughtProducts = [];
		$scope.user = {};
		$scope.user = $rootScope.getCurrentUser();
		console.log($scope.user);
		
		
		shoppingListFactory.getUsersShoppingList($scope.currentUser).success(function(data){
			$scope.total = 0;
			$scope.sp = data;
			var currentTime = new Date();		
			angular.forEach($scope.sp, function(item){
				 productsFactory.getDiscountPrice(item.productId).success(function(data){
						$rootScope.discountProducts.push(data);		
						
				 })
				 
				 productsFactory.getProduct(item.productId).success(function (data){
                	 $rootScope.products.push(data);
                	 
                	 if($rootScope.discountProducts.slice(-1)[0]) {
                	 console.log("This product exists on discount list " + $rootScope.discountProducts.slice(-1)[0].productId)
                	 $rootScope.discountProducts.slice(-1)[0].startDate = new Date($rootScope.discountProducts.slice(-1)[0].startDate.replace(/-/g,"/")); 
                	 }
                	 
                	
                	
                	 if($rootScope.discountProducts.slice(-1)[0] && $rootScope.products.slice(-1)[0].id === $rootScope.discountProducts.slice(-1)[0].productId
                			 && $rootScope.discountProducts.slice(-1)[0].startDate <= currentTime)
                	 {	
                		  $rootScope.newPrices.push($rootScope.products.slice(-1)[0].price - ($rootScope.products.slice(-1)[0].price *($rootScope.discountProducts.slice(-1)[0].discountRate/100)));
                	      $rootScope.products.slice(-1)[0].price = $rootScope.newPrices.slice(-1)[0];
                	      console.log($rootScope.products.slice(-1)[0].price)
                	 }
                	 
                	 $scope.total+=$rootScope.products.slice(-1)[0].price;
				 })	 
				 
             })
           
		})
		
		
		deliveryFactory.getDeliverers().success(function(data){
			$scope.deliverers = data;
		})
		
		productsFactory.getHistory().success(function(data){
			$scope.buyingHistory = data;
		})
		
		
		
		shoppingListFactory.getBuyingHistory($scope.currentUser).success(function(data){
			console.log(JSON.stringify(data))
			$scope.bh = data;
			angular.forEach($scope.bh,function(item){
				productsFactory.getProduct(item.productId).success(function(data){
					data.dateBought = new Date(item.date.replace(/-/g,"/"));
					$scope.boughtProducts.push(data);
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
	

			$scope.setID = function(p){
				$scope.complaint.productId = p.id; 
				$scope.complaintDeadline = new Date()
				$scope.complaintDeadline.setDate($scope.complaintDeadline.getDate()+14);
				console.log($scope.complaintDeadline)
			}
			
			$scope.sendComplaint = function(product){
			var currentDate = new Date();
			
			
			
			$scope.complaint.customerId = $scope.currentUser;
			$scope.complaint.productId = product.id;
			$scope.complaint.storeId = product.storeId;
			
			shoppingListFactory.sendComplaint($scope.complaint).success(function(){
				console.log("Zalba uspesno poslata")
				$scope.complaint = {};
			})
			
		}
	
			
		$scope.buy = function() {
		var currentDate = new Date();
		$scope.buying.customerId = $scope.currentUser;	
	
		angular.forEach($rootScope.products,function(item) { 
			
			$scope.buying.storeId = item.storeId;
			$scope.buying.productId = item.id;
			$scope.buying.deliveryId = item.deliveryId;
			$scope.buying.date = currentDate;
			$scope.buying.totalPrice = item.price;
			
			productsFactory.buy($scope.buying).success(function(){
				item.lager = item.lager-1;
				delete item.deliveryId;
				console.log(">>>>" + JSON.stringify(item))
				productsFactory.updateProduct(item);
			})
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
}).filter('rangeFilter',function() {
	return function(items,slider){
	
		var filtered = [];
		var priceMin = parseFloat(slider.min);
		var priceMax = parseFloat(slider.max);
		
		angular.forEach(items,function(item){
			if((item.price >= priceMin && item.price <= priceMax)){
				filtered.push(item);
			}
		});
		return filtered;
	};
});