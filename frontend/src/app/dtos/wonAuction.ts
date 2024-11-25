import {AuctionStatus} from "../components/enum/auction-status";

export class WonAuction {
  resourceId: string;
  status: AuctionStatus;
  createdOn: Date;
  endDate : Date;
  hash: string;
  winnerBidHash : string;

}
