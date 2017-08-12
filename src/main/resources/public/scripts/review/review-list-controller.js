
/*global define */

'use strict';

define(['angular', './review.module', '../utility/beverage-utility', './review-dao'], function (angular) {
    angular.module('beverage.review').controller('listReviewCtrl', ['$scope','$rootScope','beverage-utility','reviewDao',
        function ($scope, $rootScope, util, reviewDao) {
            var vm = this;

            vm.showBeverageDetails = showBeverageDetails;
            vm.deleteReview = deleteReview;
            vm.updateReview = updateReview;
            vm.filterReviews = util.throttle(filterReviews, 300);       
            vm.reviews = [];
            vm.loadingReviews = false;
            
            var allLoaded = false;
            var currentPage = 0;
            
            activate();
            
                        
            $scope.$on('reviewCreated', function (event, data) {
                currentPage = 0;
                allLoaded = false;
                vm.queryString = '';
                reviewDao.getReviews()
                    .then(refreshReviews);
            });
            
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
                $rootScope.$broadcast("productSelected", review);
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



