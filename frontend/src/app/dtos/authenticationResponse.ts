import {User} from "./user";

export class AuthenticationResponse  {
  errorMsg : [];
  accessToken : string;
  refreshToken: string;
  user: User;
}
