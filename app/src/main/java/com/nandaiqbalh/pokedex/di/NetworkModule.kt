package com.nandaiqbalh.pokedex.di

import com.nandaiqbalh.pokedex.data.remote.service.PokeApi
import com.nandaiqbalh.pokedex.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule  {


	@Singleton
	@Provides
	fun provideRetrofit(): Retrofit{
		val loggingInterceptor = HttpLoggingInterceptor()
			.setLevel(HttpLoggingInterceptor.Level.BODY)
		val client = OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()
	}

	@Singleton
	@Provides
	fun provideApiServicePlaces(retrofit: Retrofit): PokeApi =
		retrofit.create(PokeApi::class.java)
}