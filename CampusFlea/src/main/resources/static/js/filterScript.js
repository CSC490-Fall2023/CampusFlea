    document.addEventListener('DOMContentLoaded', function () {
      var checkboxes = document.querySelectorAll('.filter-checkbox');
      var minPriceInput = document.getElementById('min-price');
      var maxPriceInput = document.getElementById('max-price');
      var searchInput = document.getElementById('search');
      var itemsContainer = document.getElementById('grid-item');

      checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
          updateFilter();
        });
      });

      minPriceInput.addEventListener('input', function () {
        updateFilter();
      });

      maxPriceInput.addEventListener('input', function () {
        updateFilter();
      });

      searchInput.addEventListener('input', function () {
        updateFilter();
      });

      function updateFilter() {
        var selectedFilters = [];

        checkboxes.forEach(function (checkbox) {
          if (checkbox.checked) {
            selectedFilters.push(checkbox.getAttribute('data-filter'));
          }
        });

        var minPrice = parseInt(minPriceInput.value.replace(/\D/g, '')) || 0;
        var maxPrice = parseInt(maxPriceInput.value.replace(/\D/g, '')) || Infinity;
        var searchTerm = searchInput.value.toLowerCase();

        var items = itemsContainer.querySelectorAll('.neumorphic-card');

        items.forEach(function (item) {
          var itemPrice = parseInt(item.querySelector('.price').textContent.replace(/\D/g, ''));
          var itemFilters = item.className.split(' ').filter(function (className) {
            return className.startsWith('filter-');
          });
          var itemName = item.textContent.toLowerCase();

          // Check if any of the selected filters are present for the item
          if ((selectedFilters.length === 0 || selectedFilters.some(function (filter) {
            return itemFilters.includes(filter);
          })) && itemPrice >= minPrice && itemPrice <= maxPrice && itemName.includes(searchTerm)) {
            item.style.display = 'grid';
          } else {
            item.style.display = 'none';
          }
        });
      }

      function applyFilters() {
        updateFilter(); // Call the updateFilter function when applying the filters
      }
    });
