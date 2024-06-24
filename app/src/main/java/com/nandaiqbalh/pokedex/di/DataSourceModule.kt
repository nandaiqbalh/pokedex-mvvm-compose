package com.nandaiqbalh.pokedex.di

import com.nandaiqbalh.pokedex.data.remote.datasource.PokemonDataSource
import com.nandaiqbalh.pokedex.data.remote.datasource.PokemonDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

	@Binds
	abstract fun bindsPokemonDataSource(pokemonDataSourceImpl: PokemonDataSourceImpl): PokemonDataSource
}