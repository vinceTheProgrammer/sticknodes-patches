# 👋🧩 sticknodes-patches

![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)

`sticknodes-patches` generated from the template repository for ReVanced Patches.

## ❓ About

This is a collection of patches for Stick Nodes. I will push any patches I create here and possibly include patches from other authors if they wish to push their patches here too.

## 🚀 Usage

todo

## 🧑‍💻 Development
Except for the text you're reading right now and the heading, this section is left untouched from the ReVanced Patches template "Usage" instructions.

To develop and release ReVanced Patches using this template, some things need to be considered:

- Development starts in feature branches. Once a feature branch is ready, it is squashed and merged into the `dev` branch
- The `dev` branch is merged into the `main` branch once it is ready for release
- Semantic versioning is used to version ReVanced Patches. ReVanced Patches have a public API for other patches to use
- Semantic commit messages are used for commits
- Commits on the `dev` branch and `main` branch are automatically released
via the [release.yml](.github/workflows/release.yml) workflow, which is also responsible for generating the changelog
and updating the version of ReVanced Patches. It is triggered by pushing to the `dev` or `main` branch.
The workflow uses the `publish` task to publish the release of ReVanced Patches
- The `buildAndroid` task is used to build ReVanced Patches so that it can be used on Android.
The `publish` task depends on the `buildAndroid` task, so it will be run automatically when publishing a release.

## 📚 Everything else

### 📙 Contributing

Thank you for considering contributing to `sticknodes-patches`.  
You can find the contribution guidelines [here](CONTRIBUTING.md).

### 🛠️ Building

To build ReVanced Patches template (what `sticknodes-patches` was generated from),
you can follow the [ReVanced documentation](https://github.com/ReVanced/revanced-documentation).

## 📜 ReVanced Patches template Licence (what sticknodes-patches was generated from)

ReVanced Patches template is licensed under the GPLv3 licence.
Please see the [license file](LICENSE) for more information.
[tl;dr](https://www.tldrlegal.com/license/gnu-general-public-license-v3-gpl-3) you may copy, distribute
and modify ReVanced Patches template as long as you track changes/dates in source files.
Any modifications to ReVanced Patches template must also be made available under the GPL,
along with build & install instructions.
