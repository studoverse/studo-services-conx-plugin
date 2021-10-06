import { NavigationMenuResource } from '@campusonline/model';

export const INIT_MENU: NavigationMenuResource = [
  {
    label: { key: '$supersonic.home.menu-entry' },
    href: '/home',
    route: true
  },
  {
    label: { key: '$supersonic.hello.title' },
    href: '/hello',
    route: true
  },
  {
    label: { key: '$supersonic.whoami.title' },
    href: '/whoami',
    route: true
  }
];
