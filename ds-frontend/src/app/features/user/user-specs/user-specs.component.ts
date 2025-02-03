import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from '../../../core/services/user.service';
import {User} from '../../../core/models/user';
import {NgIf, NgTemplateOutlet} from '@angular/common';

@Component({
  selector: 'app-user-specs',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    NgTemplateOutlet
  ],
  templateUrl: './user-specs.component.html',
  styleUrl: './user-specs.component.css'
})
export class UserSpecsComponent implements OnInit {
  userForm: any;
  selectedUser: User | null = null;
  addEnable: boolean = false;
  editEnable: boolean = false;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userForm = new FormGroup({
      username: new FormControl({value: '', disabled: !this.addEnable && !this.editEnable}, [Validators.required]),
      email: new FormControl({value: '', disabled: !this.addEnable && !this.editEnable}, [Validators.required]),
      password: new FormControl({value: '', disabled: !this.addEnable && !this.editEnable}, [Validators.required]),
      role: new FormControl({value: '', disabled: !this.addEnable && !this.editEnable}, [Validators.required]),
      firstName: new FormControl({value: '', disabled: !this.addEnable && !this.editEnable}, [Validators.required]),
      lastName: new FormControl({value: '', disabled: !this.addEnable && !this.editEnable}, [Validators.required]),
    })
    this.onUserSelect();
  }

  private onUserSelect() {
    this.userService.selectedUser.asObservable().subscribe(user => {
      this.selectedUser = user;
      this.updateForm(user);
    })
  }

  private updateForm(user: User | null) {
    if (user) {
      this.userForm.patchValue({
        username: user.username,
        email: user.email,
        password: user.password,
        role: user.role,
        firstName: user.firstName,
        lastName: user.lastName,
      });
    } else {
      this.userForm.reset();
    }
  }

  onSelectModel(mode: string) {
    this.addEnable = mode === "add";
    this.editEnable = mode === "edit";
    this.reEnableForm();
  }

  onCancel() {
    this.disEnableForm();
    this.editEnable = false;
    this.addEnable = false;
  }

  onSave() {
    if (this.userForm.valid) {
      const username = this.userForm.get('username').value;
      const email = this.userForm.get('email').value;
      const password = this.userForm.get('password').value;
      const role = this.userForm.get('role').value;
      const firstName = this.userForm.get('firstName').value;
      const lastName = this.userForm.get('lastName').value;
      this.userService.addUser({username, email, password, role, firstName, lastName}).subscribe()
    }
  }

  onUpdate() {
    if (this.userForm.valid) {
      const username = this.userForm.get('username').value;
      const email = this.userForm.get('email').value;
      const password = this.userForm.get('password').value;
      const role = this.userForm.get('role').value;
      const firstName = this.userForm.get('firstName').value;
      const lastName = this.userForm.get('lastName').value;

      if (this.selectedUser) {
        const id = this.selectedUser.id;
        console.log(this.userForm.value)
        this.userService.updateUser(id, {username, email, password, role, firstName, lastName}).subscribe();
      }
    }
  }

  clearForm() {
    this.userForm.reset();
  }

  private reEnableForm() {
    this.userForm.get('username')?.enable();
    this.userForm.get('email')?.enable();
    this.userForm.get('password')?.enable();
    this.userForm.get('role')?.enable();
    this.userForm.get('firstName')?.enable();
    this.userForm.get('lastName')?.enable();
  }

  private disEnableForm() {
    this.userForm.get('username')?.disable();
    this.userForm.get('email')?.disable();
    this.userForm.get('password')?.disable()
    this.userForm.get('role')?.disable();
    this.userForm.get('firstName')?.disable();
    this.userForm.get('lastName')?.disable();
  }
}
