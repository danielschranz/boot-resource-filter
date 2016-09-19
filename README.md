[![License](//img.shields.io/badge/license-LGPL_v3-brightgreen.svg)](https://www.gnu.org/licenses/lgpl-3.0.en.html#content)
[![Boot](//img.shields.io/badge/boot-2.6.0-ECC42F.svg?style=flat)](http://boot-clj.com/)
[![Clojars](//img.shields.io/badge/clojars-0.1.0-blue.svg?style=flat)](https://clojars.org/....)

boot-resource-filter
==========
`[schranz/boot-resource-filter "0.1.0"]` (not yet)

A Boot task allowing you to replace tokens in resource files.

### How to test and build

* building: `boot pom jar target`

### Example usages
It is possible to run this task from the command line:
- `boot filter-resources -m ".*\.edn" -r ':test'=test -r '#"dummy"=something'`

but most likely you will want to run it from within a `build.boot`
* **TODO** example here

### Available options
```
Options:
  -h, --help                       Print this help info.
  -m, --matching REGEX             Conj REGEX onto set of regular expressions to select files for filtering.
  -r, --replacements MATCH=RESULT  Conj [MATCH RESULT] onto a map to specify which tokens to replace with which string. The files will be searched for tokens in the following formats: {{token}} . TODO allow for ${token} or @token@. TODO allow for regexps as generic string matchers.
 ```

### License
Copyright © 2016 Daniel Schranz.

Distributed under the GNU Lesser General Public License v3.
