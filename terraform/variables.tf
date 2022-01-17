variable "vpc_cidr_block" {
  default = "10.0.0.0/16"
}
variable "subnet_cidr_block" {
  default = "10.0.10.0/24"
}
variable "region" {
  default = "us-east-1"
}
variable "def_az" {
  default = "us-east-1a"
}
variable "env_prefix" {
  default = "dev"
}
variable "rt_outside" {}
variable "ssh_ip" {
  default = "151.196.124.95/32"
}

variable "instance_type" {
  default = "t2.micro"
}

variable "http_ip" {}

variable "image_name" {}
variable "public_key_location" {}
variable "entry_file" {}
