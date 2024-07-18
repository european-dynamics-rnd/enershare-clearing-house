export class PageRequest  {
    private _page:number;
    private _pageSize:number;
    private _collectionSize:number;


    constructor(page: number, pageSize: number) {
        this._page = page;
        this._pageSize = pageSize;
    }


    get page(): number {
        return this._page;
    }

    set page(value: number) {
        this._page = value;
    }

    get pageSize(): number {
        return this._pageSize;
    }

    set pageSize(value: number) {
        this._pageSize = value;
    }

    get collectionSize(): number {
        return this._collectionSize;
    }

    set collectionSize(value: number) {
        this._collectionSize = value;
    }
}
