
/*global define */

'use strict';

define(['./review.module'], function (module) {
    module.service('reviewDao', ['$http',
        function ($http) {
            
            function getReviewsWithPageAndQuery(page, query) {
                return $http.get('/v1/review', {
                    params: {
                        page: page?page:0,
                        query: query
                    }
                }).then(function (result) {
                    return result.data;
                });
            }
            
            function getReviews() {
                return getReviewsWithPageAndQuery();
            }
            
            function deleteReview(reviewId) {
                return $http.delete('/v1/review/' + reviewId);
            }
            
            function updateReview(reviewId, review) {
                return $http.put('/v1/review/' + reviewId, review);
            }
            
            function createReview(review) {
                var rateDto = {
                    description: review.description,
                    rate: review.score ? review.score : 0,
                    productId: review.productId
                };
                return $http.post('/v1/review', rateDto);
            }
            
            function getReviewForTheProduct(productId, page) {
                var url = '/v1/product_catalog/' + productId + "/reviews";
                return $http.get(url , {
                    params: {
                        page: page?page:0
                    }
                }).then(function (result) {
                    return result.data;
                });
            }
            
            return {
                getReviewsWithPageAndQuery: getReviewsWithPageAndQuery,
                getReviews: getReviews,
                deleteReview:deleteReview,
                updateReview: updateReview,
                createReview: createReview,
                getReviewForTheProduct: getReviewForTheProduct
            };

        }]);

});






