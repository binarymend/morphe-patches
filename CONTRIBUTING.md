# Contribution Guidelines

Thanks for contributing to Binarymend Patches.

## Before you start

- Check existing [issues](https://github.com/binarymend/morphe-patches/issues) before opening a new report or feature request.
- Use the repository issue templates for [bug reports](https://github.com/binarymend/morphe-patches/issues/new?labels=Bug+report&template=bug_report.yml&title=bug%3A+) and [feature requests](https://github.com/binarymend/morphe-patches/issues/new?labels=Feature+request&template=feature_request.yml&title=feat%3A+).

## Development flow

1. Open an issue first if the change is non-trivial or changes patch behavior.
2. Branch from `dev` for normal development work.
3. Keep commits focused and include context in commit messages.
4. Open pull requests against `dev`.
5. When `dev` is ready, it is promoted to `main` for the stable release channel.

## Release notes

- `dev` is the pre-release channel.
- `main` is the stable channel.
- `patches-bundle.json` and `patches-list.json` are release-managed files and should not be manually edited to inject release URLs.
