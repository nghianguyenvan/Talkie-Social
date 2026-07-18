package com.example.talkiesocial.core.network.auth;

import com.example.talkiesocial.core.common.auth.AuthManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AuthInterceptor_Factory implements Factory<AuthInterceptor> {
  private final Provider<AuthManager> authManagerProvider;

  private AuthInterceptor_Factory(Provider<AuthManager> authManagerProvider) {
    this.authManagerProvider = authManagerProvider;
  }

  @Override
  public AuthInterceptor get() {
    return newInstance(authManagerProvider.get());
  }

  public static AuthInterceptor_Factory create(Provider<AuthManager> authManagerProvider) {
    return new AuthInterceptor_Factory(authManagerProvider);
  }

  public static AuthInterceptor newInstance(AuthManager authManager) {
    return new AuthInterceptor(authManager);
  }
}
