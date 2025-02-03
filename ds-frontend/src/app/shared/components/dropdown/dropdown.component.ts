import {Component, Input} from '@angular/core';
import {NgbDropdownModule} from '@ng-bootstrap/ng-bootstrap';
import {DropdownOption} from '../../models/dropdown-option.type';
import {NgForOf} from '@angular/common';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-dropdown',
  standalone: true,
  imports: [NgbDropdownModule, NgForOf, RouterLink, RouterLinkActive],
  templateUrl: './dropdown.component.html',
  styleUrl: './dropdown.component.css'
})
export class DropdownComponent {

  @Input() placeholder: string | undefined;
  @Input() value: DropdownOption | undefined;
  @Input() options: DropdownOption[] | undefined;


}
