import { paths } from '@meta/model';
import { environment } from "@env/environment";

export const apiUri = (name: keyof paths) => environment.apiBasePath + name;
