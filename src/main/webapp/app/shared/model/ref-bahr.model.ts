import { ITypeTB } from './type-tb.model';
export const enum Style {
  TAME = 'TAME',
  WAFI = 'WAFI',
  MAJZOE = 'MAJZOE',
  MACHTOR = 'MACHTOR',
  MANHOK = 'MANHOK'
}

export interface IRefBahr {
  id?: number;
  code?: string;
  name?: string;
  signature?: string;
  style?: Style;
  parentId?: number;
  parentName?: string;
  parentStyle?: Style;
  derives?: IRefBahr[];
  rythmes?: ITypeTB[];
}

export class RefBahr implements IRefBahr {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public signature?: string,
    public style?: Style,
    public parentId?: number,
    public parentName?: string,
    public derives?: IRefBahr[],
    public rythmes?: ITypeTB[],
    public parentStyle?: Style
  ) {}
}
