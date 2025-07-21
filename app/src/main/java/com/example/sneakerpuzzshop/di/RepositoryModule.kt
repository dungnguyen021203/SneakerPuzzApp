package com.example.sneakerpuzzshop.di

import com.example.sneakerpuzzshop.data.repository.CartRepositoryImpl
import com.example.sneakerpuzzshop.data.repository.HomeRepositoryImpl
import com.example.sneakerpuzzshop.data.repository.OrderRepositoryImpl
import com.example.sneakerpuzzshop.data.repository.ProductRepositoryImpl
import com.example.sneakerpuzzshop.data.repository.ReviewRepositoryImpl
import com.example.sneakerpuzzshop.domain.repository.CartRepository
import com.example.sneakerpuzzshop.domain.repository.HomeRepository
import com.example.sneakerpuzzshop.domain.repository.OrderRepository
import com.example.sneakerpuzzshop.domain.repository.ProductRepository
import com.example.sneakerpuzzshop.domain.repository.ReviewRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    fun bindReviewRepository(reviewRepositoryImpl: ReviewRepositoryImpl): ReviewRepository

    @Binds
    fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @Binds
    fun bindOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository
}