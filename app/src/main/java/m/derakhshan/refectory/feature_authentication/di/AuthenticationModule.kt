package m.derakhshan.refectory.feature_authentication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.refectory.core.domain.utils.Constants
import m.derakhshan.refectory.feature_authentication.data.data_source.AuthenticationAPI
import m.derakhshan.refectory.feature_authentication.data.repository.AuthenticationRepositoryImpl
import m.derakhshan.refectory.feature_authentication.domain.repository.AuthenticationRepository
import m.derakhshan.refectory.feature_authentication.domain.use_cases.AuthenticationUseCase
import m.derakhshan.refectory.feature_authentication.domain.use_cases.LoginUseCase
import m.derakhshan.refectory.feature_authentication.domain.use_cases.SignUpUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {

    @Singleton
    @Provides
    fun provideAuthenticationAPI(): AuthenticationAPI {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthenticationAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideAuthenticationRepository(api: AuthenticationAPI): AuthenticationRepository {
        return AuthenticationRepositoryImpl(api = api)
    }


    @Singleton
    @Provides
    fun provideAuthenticationUseCase(repository: AuthenticationRepository): AuthenticationUseCase {
        return AuthenticationUseCase(
            loginUseCase = LoginUseCase(repository = repository),
            signUpUseCase = SignUpUseCase()
        )
    }
}