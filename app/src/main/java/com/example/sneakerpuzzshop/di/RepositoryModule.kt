package com.example.sneakerpuzzshop.di

import com.example.sneakerpuzzshop.data.repository.HomeRepositoryImpl
import com.example.sneakerpuzzshop.data.repository.ProductRepositoryImpl
import com.example.sneakerpuzzshop.domain.repository.HomeRepository
import com.example.sneakerpuzzshop.domain.repository.ProductRepository
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
}