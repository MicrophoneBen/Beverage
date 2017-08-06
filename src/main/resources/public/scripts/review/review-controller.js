
/*global define */

'use strict';

define(['angular', './review.module', './product-select.directive', './product-details.directive'
    , '../utility/beverage-utility', './review-dao', './product-dao'], function (angular) {
    angular.module('beverage.review').controller('reviewCtrl', ['beverage-utility','reviewDao','productDao',
        function (util, reviewDao, productDao) {
            var vm = this;

            vm.beverages = [];
            vm.review = {};
            vm.showBeverageDetails = showBeverageDetails;
            vm.createReview = createReview;
            vm.deleteReview = deleteReview;
            vm.updateReview = updateReview;
            vm.filterReviews = util.throttle(filterReviews, 300);       
            vm.reviews = [];
            vm.loadingReviews = false;
            
            var allLoaded = false;
            var currentPage = 0;
            
            activate();
            

            vm.loadMore = function () {
                if (allLoaded || vm.loadingReviews) {
                    return;
                }

                vm.loadingReviews = true;
                currentPage += 1;
                reviewDao.getReviewsWithPageAndQuery(currentPage, vm.queryString)
                        .then(checkIfAllLoaded)
                        .then(addReviewsToList)
                        .finally(isNotLoadingReviews);
            };

            function checkIfAllLoaded(reviews) {
                if (reviews.length === 0) {
                    allLoaded = true;
                } else {
                    allLoaded = false;
                }
                return reviews;
            }

            function addReviewsToList(reviews) {
                //vm.rates = vm.rates.concat(rates); 
                for (var i = 0; i < reviews.length; i++) {
                    vm.reviews.push(reviews[i]);
                }
            }
            
            function isNotLoadingReviews() {
                vm.loadingReviews = false;
            }

            function activate() {
                reviewDao.getReviews()
                        .then(refreshReviews)
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not load the reviews.'));
            }


            function refreshReviews(rates) {
                vm.reviews = rates;
            }

            function filterReviews() {
                currentPage = 0;
                allLoaded = false;
                reviewDao.getReviewsWithPageAndQuery(currentPage, vm.queryString).then(refreshReviews);
            }

            function showBeverageDetails(review) {
                productDao.getProduct(review.productId)
                        .then(selectProduct)
                        .then(selectTab(2))
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not get the beverage details.'));

            }
            
            function selectProduct(product) {
                vm.selectedProduct = product;
                return product;
            }

            function selectTab(tab) {
                return function () {
                    vm.activeTab = tab;
                };
            }

            function createReview() {
                currentPage = 0;
                allLoaded = false;
                vm.review.productId = vm.productToRate.productId;
                reviewDao.createReview(vm.review)
                        .then(reviewDao.getReviews)
                        .then(refreshReviews)
                        .then(selectTab(1))
                        .then(util.givePositiveFeedback('Beverage reviewed!'), util.displayErrorInformation('Could not review the beverage.'));
            }

            function deleteReview(review, index) {
                reviewDao.deleteReview(review.reviewId)
                        .then(removeRate(index))
                        .then(util.givePositiveFeedback('Review deleted.'), util.displayErrorInformation('Could not delete the review.'));
            }
            
            function removeRate(index) {
                return function () {
                    vm.reviews.splice(index, 1);
                };
            }

            function updateReview(review, index) {
                reviewDao.updateReview(review.reviewId, review)
                        .then(util.givePositiveFeedback('Review updated.'), util.displayErrorInformation('Could not update the review.'));
            }



        }]);

});



