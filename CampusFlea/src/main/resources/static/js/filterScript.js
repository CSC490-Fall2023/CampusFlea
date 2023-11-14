 document.addEventListener('DOMContentLoaded', function () {
      var checkboxes = document.querySelectorAll('.filter-checkbox');

      checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
          updateFilter();
        });
      });

      function updateFilter() {
        var selectedFilters = [];
//        console.log(selectedFilters);

        checkboxes.forEach(function (checkbox) {
          if (checkbox.checked) {
            selectedFilters.push(checkbox.getAttribute('data-filter'));
          }
        });

        var items = document.querySelectorAll('.neumorphic-card');

         items.forEach(function (item) {
                  var itemFilter = item.className.split(' ').find(function (className) {
                    return className.startsWith('filter-');
                  });

                  if (!itemFilter || selectedFilters.includes(itemFilter) || selectedFilters.length === 0) {
                    item.style.display = 'grid';
                  } else {
                    item.style.display = 'none';
                  }
                });
//          if (selectedFilters.length === 0 || selectedFilters.every(function (filter)                     {
//                        return itemFilters.includes(filter);
//                    }
//                )
//            ) {
//            item.style.display = 'grid';
//          } else {
//            item.style.display = 'none';
//          }
//        });
      }
    });