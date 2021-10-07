/**
 * This file was auto-generated by openapi-typescript.
 * Do not make direct changes to the file.
 */

export interface paths {
  "/role-examples": {
    get: {
      parameters: {
        query: {
          "org-id": string;
        };
      };
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": components["schemas"]["RoleExampleResource"];
          };
        };
      };
    };
  };
  "/auth/authn/login/{any}": {
    get: {
      parameters: {
        path: {
          any: string;
        };
      };
      responses: {
        /** OK */
        200: unknown;
      };
    };
  };
  "/auth/authz/logout": {
    get: {
      responses: {
        /** OK */
        200: unknown;
      };
    };
  };
  "/auth-demo/subject": {
    get: {
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": components["schemas"]["CachedAuthRolesOfIdentitySetResource"];
          };
        };
      };
    };
  };
  "/greetings": {
    get: {
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": components["schemas"]["GreetingListResource"];
          };
        };
      };
    };
  };
  "/auth/authn/logout": {
    get: {
      responses: {
        /** OK */
        200: unknown;
      };
    };
  };
  "/auth/authz/redirection{any}": {
    get: {
      parameters: {
        path: {
          any: string;
        };
        query: {
          session_code?: string;
        };
      };
      responses: {
        /** OK */
        200: unknown;
      };
    };
  };
  "/translations/{language}": {
    get: {
      parameters: {
        path: {
          language: string;
        };
        query: {
          bundles?: string[];
        };
      };
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": string;
          };
        };
      };
    };
  };
  "/auth/authz/login": {
    get: {
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": components["schemas"]["Session"];
          };
        };
      };
    };
  };
  "/auth/authn/post-logout": {
    get: {
      parameters: {
        query: {
          state?: string;
        };
      };
      responses: {
        /** OK */
        200: unknown;
      };
    };
  };
  "/auth/authz/authorization{any}": {
    get: {
      parameters: {
        path: {
          any: string;
        };
      };
      responses: {
        /** OK */
        200: unknown;
      };
    };
  };
  "/user/desktop": {
    get: {
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": components["schemas"]["DesktopResource"];
          };
        };
      };
    };
  };
  "/auth-demo/session": {
    get: {
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": components["schemas"]["Session"];
          };
        };
      };
    };
  };
  "/greetings/{example-id}": {
    get: {
      parameters: {
        path: {
          "example-id": number;
        };
      };
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": components["schemas"]["GreetingResource"];
          };
        };
      };
    };
  };
  "/role-examples/check-demo-1": {
    get: {
      parameters: {
        query: {
          "org-id": string;
        };
      };
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": string;
          };
        };
      };
    };
  };
  "/auth/authz/session": {
    get: {
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": components["schemas"]["Session"];
          };
        };
      };
    };
  };
  "/role-examples/check-demo-2": {
    get: {
      parameters: {
        query: {
          "org-id": string;
        };
      };
      responses: {
        /** OK */
        200: {
          content: {
            "application/json": string;
          };
        };
      };
    };
  };
}

export interface components {
  schemas: {
    AuthIdentityResource: {
      externalPersonId?: number;
      internalIdentityId?: number;
      name?: string;
      obfuscatedIdentityId?: string;
      staffId?: number;
      studentId?: number;
    };
    AuthRolesOfIdentitySetResource: {
      items?: string[];
      identity?: components["schemas"]["AuthIdentityResource"];
    };
    CachedAuthRolesOfIdentitySetResource: {
      cachedEntity?: components["schemas"]["AuthRolesOfIdentitySetResource"];
      debug?: string;
      exp?: number;
      fromCache?: boolean;
      iat?: number;
      sessionReference?: string;
    };
    DesktopFooterResource: {
      info?: components["schemas"]["I18nTextResource"];
      links?: components["schemas"]["UiLinkResource"][];
    };
    DesktopHeaderResource: {
      homeLink?: components["schemas"]["UiLinkResource"];
      logoLink?: components["schemas"]["UiLinkResource"];
    };
    DesktopMetaResource: {
      customThemeLink?: components["schemas"]["EmbeddedLinkResource"];
      systemThemeLink?: components["schemas"]["EmbeddedLinkResource"];
    };
    DesktopResource: {
      footer?: components["schemas"]["DesktopFooterResource"];
      header?: components["schemas"]["DesktopHeaderResource"];
      meta?: components["schemas"]["DesktopMetaResource"];
    };
    EmbeddedLinkResource: {
      href?: string;
      type?: components["schemas"]["EmbeddedLinkType"];
    };
    EmbeddedLinkType: "CSS" | "IMAGE" | "SCRIPT";
    /** All erroneous requests result in this CAMPUSonline specific error resource. */
    ErrorResource: {
      detail?: string;
      /** an understandable technical error message with additional error specific information in a string map. */
      diagnosticContext?: { [key: string]: string };
      instance?: string;
      /** The http status code is included in the error resource. */
      status?: number;
      title?: string;
      type: string;
    };
    GreetingListResource: {
      items?: components["schemas"]["GreetingResource"][];
    };
    GreetingResource: {
      id?: number;
      text?: string;
    };
    I18nTextResource: {
      key: string;
      replacements?: { [key: string]: string };
    };
    /** Generic resource to track modifications (create, update, delete) of a specific resource */
    ModificationResource: {
      modifiedAt?: string;
      modifiedByClientId?: string;
      modifiedByPersonUid?: string;
      type?: components["schemas"]["ModificationType"] & unknown;
    };
    ModificationType: "CREATE" | "DELETE" | "UPDATE";
    RoleExampleResource: {
      exampleRead?: boolean;
    };
    Session: {
      debug?: string;
      language?: string;
      obfuscatedIdentityId?: string;
      requiresRedirectToSessionProvider?: boolean;
      sessionReference?: string;
      userGroup?: string;
      valid?: boolean;
    };
    UiLinkResource: {
      href?: string;
      label?: components["schemas"]["I18nTextResource"];
      target?: string;
    };
    MapStringString: { [key: string]: string };
    MapStringSetString: { [key: string]: string[] };
  };
}

export interface operations {}

export interface external {}
