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
public final class AuthAuthenticator_Factory implements Factory<AuthAuthenticator> {
  private final Provider<AuthManager> authManagerProvider;

  private AuthAuthenticator_Factory(Provider<AuthManager> authManagerProvider) {
    this.authManagerProvider = authManagerProvider;
  }

  @Override
  public AuthAuthenticator get() {
    return newInstance(authManagerProvider.get());
  }

  public static AuthAuthenticator_Factory create(Provider<AuthManager> authManagerProvider) {
    return new AuthAuthenticator_Factory(authManagerProvider);
  }

  public static AuthAuthenticator newInstance(AuthManager authManager) {
    return new AuthAuthenticator(authManager);
  }
}
