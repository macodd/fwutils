import { WeaponType } from './weapontype';
import { WikiItem } from '../common/wikiitem';

export interface Weapon extends WikiItem {
  strength: number;
  isNondurable: boolean;
  requiredPower: number;
  requiredIntelligence: number;
  requiredCourses: number;
  weaponType?: WeaponType;
}
