import { themes as prismThemes } from "prism-react-renderer";
import type { Config } from "@docusaurus/types";
import type * as Preset from "@docusaurus/preset-classic";

const config: Config = {
  title: "GravatarJavaClient",
  tagline: "A JVM client for Gravatar Avatar and Profile APIs",
  favicon: "img/favicon.ico",

  future: {
    v4: true,
  },

  url: "https://nathancheshire.github.io",
  baseUrl: "/GravatarJavaClient/",
  organizationName: "nathancheshire",
  projectName: "GravatarJavaClient",
  trailingSlash: false,
  onBrokenLinks: "throw",
  i18n: {
    defaultLocale: "en",
    locales: ["en"],
  },

  presets: [
    [
      "classic",
      {
        docs: {
          sidebarPath: "./sidebars.ts",
        },
        blog: false,
        theme: {
          customCss: "./src/css/custom.css",
        },
      } satisfies Preset.Options,
    ],
  ],

  plugins: [
    [
      "@docusaurus/plugin-client-redirects",
      {
        redirects: [
          {
            from: "/",
            to: "/docs/intro",
          },
          {
            from: "/docs",
            to: "/docs/intro",
          },
        ],
      },
    ],
  ],

  themeConfig: {
    colorMode: {
      defaultMode: "light",
      disableSwitch: true,
      respectPrefersColorScheme: false,
    },
    navbar: {
      title: "GravatarJavaClient",
      logo: {
        alt: "GravatarJavaClient Logo",
        src: "img/logo.png",
        href: "/docs/intro",
      },
      items: [
        {
          type: "docSidebar",
          sidebarId: "docsSidebar",
          position: "left",
          label: "Documentation",
        },
        {
          href: "https://github.com/nathancheshire/GravatarJavaClient",
          label: "GitHub",
          position: "right",
        },
      ],
    },
    footer: {
      style: "light",
      copyright: `Copyright © 2023 - 2025 GravatarJavaClient. Docs built with Docusaurus.`,
    },
    prism: {
      theme: prismThemes.github,
      darkTheme: prismThemes.github,
      additionalLanguages: ["java", "gradle"],
    },
  } satisfies Preset.ThemeConfig,
};

export default config;
