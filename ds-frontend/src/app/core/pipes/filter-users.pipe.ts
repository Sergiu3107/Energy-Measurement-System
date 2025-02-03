import {Pipe, PipeTransform} from '@angular/core';
import {User} from '../models/user';

@Pipe({
  standalone: true,
  name: 'filterUsers'
})
export class FilterUsersPipe implements PipeTransform {
  transform(items: User[] | undefined, searchText: string): any[] | undefined {
    if (!items) return [];
    if (searchText == '') return items;

    searchText = searchText.toLowerCase();
    return items.filter((item: User) => {
      return item.username.toLowerCase().includes(searchText);
    });


  }
}
