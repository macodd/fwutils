import { WikiItem } from '../common/wikiitem';

export interface Ability extends WikiItem {
  baseTime: number;
  maxLevel: number;
}
