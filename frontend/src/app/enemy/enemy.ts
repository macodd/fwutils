import { WikiItem } from '../common/wikiitem';

export interface Enemy extends WikiItem {
  minStrength: number;
  maxStrength: number;
  minHealth: number;
  maxHealth: number;
  droppedMoney?: number;
}
