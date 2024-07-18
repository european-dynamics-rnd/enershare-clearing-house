import {User} from "./user";

export interface Page<T> {
    content: T[];
    totalElements: number
  // pageable: {
  //   pageNumber: number,
  //   pageSize: number
  //   sort: {
  //     empty: boolean,
  //     sorted: boolean,
  //     unsorted: boolean
  //   },
  //   offset: number,
  //   paged: boolean,
  //   unpaged: boolean
  // },
  // last: boolean,
  // totalPages: number,
  // totalElements: number,
  // first: boolean,
  // size: number,
  // number: number,
  // sort: {
  //   empty: boolean,
  //   sorted: boolean,
  //   unsorted: boolean
  // },
  // numberOfElements: number,
  // empty: boolean
}
