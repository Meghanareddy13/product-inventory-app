import { useState, useEffect } from 'react';

function ProductList() {
  const [productId] = useState(1);
  const [stock, setStock] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    let ignore = false;
    fetch(`http://localhost:8080/api/inventory/${productId}`)
      .then(res => {
        if (!res.ok) throw new Error('Failed to fetch stock');
        return res.json();
      })
      .then(data => { if (!ignore) setStock(data); })
      .catch(err => setError(err.message));
    return () => { ignore = true; };
  }, [productId]);

  if (error) return <p>Error: {error}</p>;
  if (stock === null) return <p>Loading...</p>;
  return <p>Available stock: {stock}</p>;
}
export default ProductList;
