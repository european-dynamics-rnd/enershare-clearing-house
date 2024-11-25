import {AuctionStatus} from "../components/enum/auction-status";

export class ProposedAuction {
  resourceId: string;
  status: AuctionStatus;
  createdOn: Date;
  endDate : Date;
  hash: string;
}
