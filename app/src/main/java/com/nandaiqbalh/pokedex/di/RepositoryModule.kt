package com.nandaiqbalh.pokedex.di

import com.nandaiqbalh.pokedex.data.remote.repository.PokemonRepository
import com.nandaiqbalh.pokedex.data.remote.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

	@Binds
	abstract fun bindsPokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository
}